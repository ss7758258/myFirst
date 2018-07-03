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
        "select count(1) from weixin_user where open_id = #{0}"
    })
    Integer selectUserCountByOpenId(String openId);

    /**
     * 根据openid更新该用户是否已选择星座
     * @param constellationId
     * @param openId
     * @param updateTime
     * @return
     */
    @Update({
        "update weixin_user set constellation_id = #{0}, nick_name = #{2}, head_image = #{1}, update_timestamp = #{3} where open_id = #{4}"
    })
    void updateMyConstellationByOpenId(Long constellationId, String headImage, String nickName, String updateTime, String openId);
}
