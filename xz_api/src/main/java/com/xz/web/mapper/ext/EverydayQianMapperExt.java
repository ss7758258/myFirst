package com.xz.web.mapper.ext;

import com.xz.web.mapper.entity.TiQianList;
import com.xz.web.mapper.entity.TiUserQianList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EverydayQianMapperExt {

    @Select({
            "<script>",
            "select * from ti_user_qian_list",
            "</script>"
    })
    List<TiUserQianList> testSelect();

    @Insert({
            "<script>",
            "INSERT INTO `ti_user_qian_list` (`qian_date`, `status`, `qian_name`, `qian_content`, `user_id`, `friend_open_id1`, `friend_open_id2`, `friend_open_id3`, `friend_open_id4`, `friend_open_id5`, `create_timestamp`, `update_timestamp`) " +
                    "VALUES " +
                    "(#{qianDate}, #{status}, #{qianName}, #{qianContent}, #{userId}, #{friendOpenId1}, #{friendOpenId2}, #{friendOpenId3}, #{friendOpenId4}, #{friendOpenId5}, #{createTimestamp}, #{updateTimestamp});",
            "</script>"
    })
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insertData(TiUserQianList obj);


    @Select({
            "<script>",
            "select count(*) as counts from ti_qian_list,ti_qian_lib where ti_qian_lib.id=ti_qian_list.qian_lib_id and ti_qian_lib.status=1",
            "</script>"
    })
    Long countActiveQianList();
    @Select({
            "<script>",
            "select ti_qian_list.* from ti_qian_list,ti_qian_lib where ti_qian_lib.id=ti_qian_list.qian_lib_id and ti_qian_lib.status=1 limit #{0},1",
            "</script>"
    })
    TiQianList randomActiveQianList(Integer randomNum);
}
