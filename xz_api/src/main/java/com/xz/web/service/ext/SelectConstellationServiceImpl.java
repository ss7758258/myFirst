package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.enums.AjaxStatus;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.framework.common.base.BeanCriteria;
import com.xz.framework.utils.DateUtil;
import com.xz.framework.utils.JsonUtil;
import com.xz.web.bo.selectConstellation.X100Bo;
import com.xz.web.dao.redis.RedisDao;
import com.xz.web.mapper.entity.TcConstellation;
import com.xz.web.mapper.entity.TcQianYanUrl;
import com.xz.web.mapper.entity.TiLucky;
import com.xz.web.mapper.entity.WeixinUser;
import com.xz.web.mapper.ext.SelectConstellationMapperExt;
import com.xz.web.service.TcConstellationService;
import com.xz.web.service.TcQianYanUrlService;
import com.xz.web.service.TiLuckyService;
import com.xz.web.service.WeixinUserService;
import com.xz.web.vo.selectConstellation.X100Vo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectConstellationServiceImpl implements SelectConstellationService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SelectConstellationMapperExt selectConstellationMapperExt;
    @Autowired
    private WeixinUserService weixinUserService;
    @Autowired
    private TcConstellationService tcConstellationService;
    @Autowired
    private TcQianYanUrlService tcQianYanUrlService;
    @Autowired
    private TiLuckyService tiLuckyService;

    @Autowired
    private RedisDao redisService;

    @Value("#{constants.redis_key_time}")
    private int redisKeyTime;

    /**
     * 选择星座后返回首页
     * @param x100Vo
     * @Param weixin
     * @return
     */
    @Override
    public XZResponseBody<X100Bo> saveConstellation(X100Vo x100Vo, Weixin weixin) throws Exception{
        XZResponseBody<X100Bo> responseBody = new XZResponseBody<X100Bo>();
        X100Bo x100Bo = new X100Bo();
        //查询该用户是否已选择星座，如果没有选择，则insert，否则update
        Integer count = selectConstellationMapperExt.selectUserCountByOpenId(weixin.getOpenId());
        String updateTime = DateUtil.getDatetime();
        if (0 == count){
            //insert
            WeixinUser weixinUser = new WeixinUser();
            weixinUser.setHeadImage(x100Vo.getHeadImage());
            weixinUser.setNickName(x100Vo.getNickName());
            weixinUser.setOpenId(weixin.getOpenId());
            weixinUser.setConstellationId(x100Vo.getConstellationId());
            weixinUser.setIsDisabled("0");
            weixinUser.setUpdateTimestamp(updateTime);
            weixinUser.setCreateTimestamp(updateTime);
            weixinUserService.save(weixinUser);
        }else {
            //update
            selectConstellationMapperExt.updateMyConstellationByOpenId(x100Vo.getConstellationId(), x100Vo.getHeadImage(), x100Vo.getNickName(), updateTime, weixin.getOpenId());
        }
        /**
         * 星座名、星座头像、月份；
         * 星座指数；
         * 今日提醒（一条）；
         * 一签一言图片；
         */
        TcConstellation tcConstellation = new TcConstellation();
        TcQianYanUrl tcQianYanUrl = new TcQianYanUrl();

        //根据openid查询星座信息，key格式为  constellation-:openid
        if (redisService.hasKey("constellation-:" + weixin.getOpenId())){
            String str = redisService.get("constellation-:" + weixin.getOpenId());
            tcConstellation =  JsonUtil.deserialize(str, TcConstellation.class);
        }else {
            //查询当前openid的星座信息
            tcConstellation = tcConstellationService.selectByKey(x100Vo.getConstellationId());
            String redisJson = JsonUtil.serialize(tcConstellation);
            redisService.set("constellation-:" + weixin.getOpenId(), redisJson, redisKeyTime);
        }
        if (null != tcConstellation) {
            x100Bo.setConstellationId(tcConstellation.getId());
            x100Bo.setConstellationName(tcConstellation.getConstellationName());
            x100Bo.setEndDate(tcConstellation.getEndDate());
            x100Bo.setStartDate(tcConstellation.getStartDate());
            x100Bo.setPictureUrl(tcConstellation.getPictureUrl());
        }

        //一言一签图片，key格式为  qianyanUrl
        if (redisService.hasKey("qianyanUrl")){
            String str = redisService.get("qianyanUrl");
            tcQianYanUrl =  JsonUtil.deserialize(str, TcQianYanUrl.class);
        }else {
            List<TcQianYanUrl> tcQianYanUrlList = tcQianYanUrlService.selectByExample(null);
            if (!tcQianYanUrlList.isEmpty()) {
                tcQianYanUrl = tcQianYanUrlList.get(0);
                String redisJson = JsonUtil.serialize(tcQianYanUrl);
                redisService.set("qianyanUrl", redisJson, redisKeyTime);
            }
        }
        if (null != tcQianYanUrl) {
            x100Bo.setQianUrl(tcQianYanUrl.getQianUrl());
            x100Bo.setYanUrl(tcQianYanUrl.getYanUrl());
        }

        //更加星座id查询对应的信息, redis key格式为  lucky-:constellationId
        TiLucky tiLucky = new TiLucky();
        if (redisService.hasKey("lucky-:" + x100Vo.getConstellationId())){
            String str = redisService.get("lucky-:" + x100Vo.getConstellationId());
            tiLucky =  JsonUtil.deserialize(str, TiLucky.class);
        }else {
            //查询当前openid的运势信息
            BeanCriteria beanCriteria = new BeanCriteria(TiLucky.class);
            BeanCriteria.Criteria criteria = beanCriteria.createCriteria();
            criteria.andEqualTo("constellationId", x100Vo.getConstellationId());
            criteria.andEqualTo("status", 1);
            beanCriteria.setOrderByClause("publish_time desc");
            List<TiLucky> tiLuckyList = tiLuckyService.selectByExample(beanCriteria);
            if (!tiLuckyList.isEmpty()) {
                tiLucky = tiLuckyList.get(0);
                String redisJson = JsonUtil.serialize(tiLucky);
                redisService.set("lucky-:" + x100Vo.getConstellationId(), redisJson, redisKeyTime);
            }
        }
        if (null != tiLucky) {
            x100Bo.setLuckyScore1(tiLucky.getLuckyScore1() + "%");
            x100Bo.setLuckyScore2(tiLucky.getLuckyScore2() + "%");
            x100Bo.setLuckyScore3(tiLucky.getLuckyScore3() + "%");
            x100Bo.setLuckyScore4(tiLucky.getLuckyScore4() + "%");
            x100Bo.setLuckyType1(tiLucky.getLuckyType1());
            x100Bo.setLuckyType2(tiLucky.getLuckyType2());
            x100Bo.setLuckyType3(tiLucky.getLuckyType3());
            x100Bo.setLuckyType4(tiLucky.getLuckyType4());
        }

        responseBody.setStatus(AjaxStatus.SUCCESS);
        responseBody.setData(x100Bo);
        return responseBody;
    }

}
