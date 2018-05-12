package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.enums.AjaxStatus;
import com.xz.framework.common.base.BeanCriteria;
import com.xz.web.bo.moreConstellation.X300Bo;
import com.xz.web.mapper.entity.TiLucky;
import com.xz.web.mapper.ext.MoreConstellationMapperExt;
import com.xz.web.service.TiLuckyService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoreConstellationServiceImpl implements MoreConstellationService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MoreConstellationMapperExt moreConstellationMapperExt;
    @Autowired
    private TiLuckyService tiLuckyService;

    /**
     * 返回星座运势（更多）
     * @return
     * @param constellationId
     */
    @Override
    public XZResponseBody<X300Bo> selectMoreConstellation(Long constellationId) throws Exception {
        XZResponseBody<X300Bo> responseBody = new XZResponseBody<X300Bo>();
        X300Bo x300Bo = new X300Bo();
        x300Bo.setConstellationId(constellationId);
        /**
         * 接口返回运势指数；
         * 去做内容（标题及内容）；
         * 别做内容（标题及内容）；
         */

        BeanCriteria beanCriteria = new BeanCriteria(TiLucky.class);
        BeanCriteria.Criteria criteria = beanCriteria.createCriteria();
        criteria.andCondition("constellationId", constellationId);
        List<TiLucky> tiLuckyList = tiLuckyService.selectByExample(beanCriteria);
        if (!tiLuckyList.isEmpty()){
            x300Bo.setLuckyScoreMore1(tiLuckyList.get(0).getLuckyScoreMore1() + "");
            x300Bo.setLuckyScoreMore2(tiLuckyList.get(0).getLuckyScoreMore2() + "");
            x300Bo.setLuckyScoreMore3(tiLuckyList.get(0).getLuckyScoreMore3() + "");
            x300Bo.setLuckyScoreMore4(tiLuckyList.get(0).getLuckyScoreMore4() + "");

            x300Bo.setLuckyTypeMore1(tiLuckyList.get(0).getLuckyTypeMore1());
            x300Bo.setLuckyTypeMore2(tiLuckyList.get(0).getLuckyTypeMore2());
            x300Bo.setLuckyTypeMore3(tiLuckyList.get(0).getLuckyTypeMore3());
            x300Bo.setLuckyTypeMore4(tiLuckyList.get(0).getLuckyTypeMore4());

            x300Bo.setLuckyWords1(tiLuckyList.get(0).getLuckyWords1());
            x300Bo.setLuckyWords2(tiLuckyList.get(0).getLuckyWords2());
            x300Bo.setLuckyWords3(tiLuckyList.get(0).getLuckyWords3());
            x300Bo.setLuckyWords4(tiLuckyList.get(0).getLuckyWords4());

            x300Bo.setToDo(tiLuckyList.get(0).getToDo());
            x300Bo.setNotDo(tiLuckyList.get(0).getNotDo());
            x300Bo.setPublishTime(tiLuckyList.get(0).getPublishTime());
            x300Bo.setLuckyDate(tiLuckyList.get(0).getLuckyDate());
            x300Bo.setCreateTime(tiLuckyList.get(0).getCreateTimestamp());
        }

        responseBody.setStatus(AjaxStatus.SUCCESS);
        responseBody.setData(x300Bo);
        return responseBody;
    }
}
