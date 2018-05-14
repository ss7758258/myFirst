package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.enums.AjaxStatus;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.framework.common.base.BeanCriteria;
import com.xz.web.bo.selectConstellation.X100Bo;
import com.xz.web.mapper.entity.TcConstellation;
import com.xz.web.mapper.entity.TcQianYanUrl;
import com.xz.web.mapper.entity.TiLucky;
import com.xz.web.mapper.ext.EverydayWordsMapperExt;
import com.xz.web.mapper.ext.SelectConstellationMapperExt;
import com.xz.web.service.TcConstellationService;
import com.xz.web.service.TcQianYanUrlService;
import com.xz.web.service.TiLuckyService;
import com.xz.web.service.WeixinUserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexConstellationServiceImpl implements IndexConstellationService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SelectConstellationMapperExt selectConstellationMapperExt;

    @Autowired
    private TcConstellationService tcConstellationService;
    @Autowired
    private TcQianYanUrlService tcQianYanUrlService;
    @Autowired
    private TiLuckyService tiLuckyService;

    @Autowired
    private EverydayWordsMapperExt everydayWordsMapperExt;

    /**
     * 返回首页（不用传参的，用户之前已经选择过星座）
     *
     * @param weixin
     * @return
     */
    @Override
    public XZResponseBody<X100Bo> selectMyConstellation(Weixin weixin) throws Exception {
        XZResponseBody<X100Bo> responseBody = new XZResponseBody<X100Bo>();
        X100Bo x100Bo = new X100Bo();
        /**
         * 星座名、星座头像、月份；
         * 星座指数；
         * 今日提醒（一条）；
         * 一签一言图片；
         */
        Long constellationId = everydayWordsMapperExt.selectConstellationIdByOpenId(weixin.getOpenId());

        TcConstellation tcConstellation = tcConstellationService.selectByKey(constellationId);
        x100Bo.setConstellationId(tcConstellation.getId());
        x100Bo.setConstellationName(tcConstellation.getConstellationName());
        x100Bo.setEndDate(tcConstellation.getEndDate());
        x100Bo.setStartDate(tcConstellation.getStartDate());
        x100Bo.setPictureUrl(tcConstellation.getPictureUrl());

        List<TcQianYanUrl> tcQianYanUrlList = tcQianYanUrlService.selectByExample(null);
        if (!tcQianYanUrlList.isEmpty()) {
            x100Bo.setQianUrl(tcQianYanUrlList.get(0).getQianUrl());
            x100Bo.setYanUrl(tcQianYanUrlList.get(0).getYanUrl());
        }

        BeanCriteria beanCriteria = new BeanCriteria(TiLucky.class);
        BeanCriteria.Criteria criteria = beanCriteria.createCriteria();
        criteria.andEqualTo("constellationId", constellationId);
        criteria.andEqualTo("status", 1);
        List<TiLucky> tiLuckyList = tiLuckyService.selectByExample(beanCriteria);
        if (!tiLuckyList.isEmpty()) {
            x100Bo.setLuckyScore1(tiLuckyList.get(0).getLuckyScore1() + "%");
            x100Bo.setLuckyScore2(tiLuckyList.get(0).getLuckyScore2() + "%");
            x100Bo.setLuckyScore3(tiLuckyList.get(0).getLuckyScore3() + "%");
            x100Bo.setLuckyScore4(tiLuckyList.get(0).getLuckyScore4() + "%");
            x100Bo.setLuckyType1(tiLuckyList.get(0).getLuckyType4());
            x100Bo.setLuckyType2(tiLuckyList.get(0).getLuckyType4());
            x100Bo.setLuckyType3(tiLuckyList.get(0).getLuckyType4());
            x100Bo.setLuckyType4(tiLuckyList.get(0).getLuckyType4());
        }

        responseBody.setStatus(AjaxStatus.SUCCESS);
        responseBody.setData(x100Bo);
        return responseBody;
    }
}
