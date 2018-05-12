package com.xz.web.mapper.ext;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SelectConstellationMapperExt {
    /**
     * 根据openid查询该用户是否已选择星座
     * @param openId
     * @return
     */
    @Select({

    })
    Integer selectUserCountByOpenId(String openId);

    /**
     * 根据openid更新该用户是否已选择星座
     * @param constellationId
     * @param openId
     * @return
     */
    @Update({

    })
    void updateMyConstellationByOpenId(String constellationId, String openId);
}
