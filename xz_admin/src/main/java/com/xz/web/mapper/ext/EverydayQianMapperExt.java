package com.xz.web.mapper.ext;

import com.xz.web.entity.TiQianList;
import org.apache.ibatis.annotations.Select;

public interface EverydayQianMapperExt {

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
