package com.xz.web.mapper.ext;

import com.xz.web.bo.f30.F20Bo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MyCollectionViewExtMapper {

    @Select({
            " select t1.art_id as artId, t1.id, t1.create_open_id as createUser, t1.title, t1.content, t2.favorite_num as collectCount,",
            " t2.forward_num as shareCount, t2.praise_num as likeCount, path as backgroundUrl, t2.create_time as createTime",
            " from my_collection_view as t1",
            " left join article as t2 on t1.art_id=t2.art_id",
            " LEFT JOIN sys_file as t3 on background_url = fid",
            " where t1.create_open_id = #{0}",
            " order by t1.create_time desc"
    })
    List<F20Bo> selectMyCollectionByUserId(String openId);
}
