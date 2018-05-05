package com.xz.web.mapper.ext;

import com.xz.web.mapper.entity.SysSeqUid;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SequenceExtMapper {
    
    @Update({
        "update sys_seq_uid",
        " set seq_uid = #{seq_uid}",
        " where module = #{module}"
    })
    int updateByModule(@Param("module") String module, @Param("seq_uid") long uid);
    
    @Select({
        "select",
        " seq_id as seqId, module, seq_uid as seqUid, max_unit as maxUnit",
        " from sys_seq_uid",
        " where module = #{module}"
    })
    SysSeqUid selectByModule(@Param("module") String module);
}