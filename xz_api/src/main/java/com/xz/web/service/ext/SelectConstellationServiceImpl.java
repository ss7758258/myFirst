package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.web.bo.selectConstellation.X100Bo;
import com.xz.web.mapper.ext.SelectConstellationMapperExt;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectConstellationServiceImpl implements SelectConstellationService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SelectConstellationMapperExt selectConstellationMapperExt;


    /**
     * 选择星座后返回首页
     * @param constellationId
     * @Param weixin
     * @return
     */
    @Override
    public XZResponseBody<X100Bo> saveConstellation(String constellationId, Weixin weixin) throws Exception{
        XZResponseBody<X100Bo> responseBody = new XZResponseBody<X100Bo>();
        X100Bo x100Bo = new X100Bo();
        //查询该用户是否已选择星座，如果没有选择，则insert，否则update
        Integer count = selectConstellationMapperExt.selectUserCountByOpenId(weixin.getOpenId());
        if (0 == count){
            //insert
        }else {
            //update
            selectConstellationMapperExt.updateMyConstellationByOpenId(constellationId, weixin.getOpenId());
        }
        /**
         * 星座名、星座头像、月份；
         * 星座指数；
         * 今日提醒（一条）；
         * 一签一言图片；
         */

        return null;
    }
}
