package com.yeting.web.mapper;

import com.yeting.framework.common.base.BaseMapper;
import com.yeting.web.mapper.entity.WeixinUserArtLike;
import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface WeiXinUserArtLikeMapper extends BaseMapper<WeixinUserArtLike> {
}