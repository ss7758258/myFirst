package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.web.bo.everydayQian.X500Bo;
import com.xz.web.mapper.entity.TiQianList;
import com.xz.web.mapper.entity.TiUserQianList;
import com.xz.web.mapper.ext.EverydayQianMapperExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EverydayQianServiceImpl implements EverydayQianService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EverydayQianMapperExt everydayQianMapperExt;

    /**
     * 每日一签
     * @param
     * @return
     */
    @Override
    public XZResponseBody<X500Bo> selectEverydayQian() {
        /**
         *  当天时间（有英文）；
         * 星座图片、描述（是否有多条）；
         * 星座总结话；
         */
        return null;
    }

    /**
     * 保存一言图片
     * @return
     */
    @Override
    public XZResponseBody<String> saveEverydayQian() {
        return null;
    }

    @Override
    public int save(TiUserQianList obj) {
        return everydayQianMapperExt.insertData(obj);
    }

    @Override
    public List<TiUserQianList> testSelect() {
        return everydayQianMapperExt.testSelect();
    }

    @Override
    public long countActiveQianList() {
        return everydayQianMapperExt.countActiveQianList();
    }

    @Override
    public TiQianList randomActiveQianList(int randomNum) {
        return everydayQianMapperExt.randomActiveQianList(randomNum);
    }
}
