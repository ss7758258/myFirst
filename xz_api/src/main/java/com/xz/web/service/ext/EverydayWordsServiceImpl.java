package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.enums.AjaxStatus;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.framework.utils.StringUtil;
import com.xz.web.bo.everydayWords.X400Bo;
import com.xz.web.dao.redis.RedisDao;
import com.xz.web.mapper.ext.EverydayWordsMapperExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EverydayWordsServiceImpl implements EverydayWordsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EverydayWordsMapperExt everydayWordsMapperExt;
    @Autowired
    private RedisDao redisService;

    /**
     * 每日一言
     * @param
     * @param weixin
     * @return
     */
    @Override
    public XZResponseBody<X400Bo> selectEverydayWords(Weixin weixin) {
        /**
         *  当天时间（有英文）；
         * 星座图片、描述（是否有多条）；
         * 星座总结话；
         */
        Long constellationId = everydayWordsMapperExt.selectConstellationIdByOpenId(weixin.getOpenId());
        XZResponseBody<X400Bo> response = new XZResponseBody<X400Bo>();
        X400Bo x400Bo = everydayWordsMapperExt.selectCurrentYanByConstellationId(constellationId);

        String ownerNickName = redisService.get("nickName-:" + weixin.getOpenId());
        ownerNickName = StringUtil.Base64ToStr(ownerNickName);
        x400Bo.setNickName(ownerNickName);

        response.setStatus(AjaxStatus.SUCCESS);
        response.setData(x400Bo);
        return response;
    }

    /**
     * 保存一言图片
     * @return
     */
    @Override
    public XZResponseBody<String> saveEverydayWords() {
        return null;
    }
}
