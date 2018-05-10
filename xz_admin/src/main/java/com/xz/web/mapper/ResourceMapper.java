package com.xz.web.mapper;

import com.xz.web.entity.Resource;
import com.xz.web.entity.ResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface ResourceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @SelectProvider(type=ResourceSqlProvider.class, method="countByExample")
    int countByExample(ResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @DeleteProvider(type=ResourceSqlProvider.class, method="deleteByExample")
    int deleteByExample(ResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @Delete({
        "delete from resource",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @Insert({
        "insert into resource (resource_name, resource_code, ",
        "status, create_timestamp, ",
        "update_timestamp)",
        "values (#{resourceName,jdbcType=VARCHAR}, #{resourceCode,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=INTEGER}, #{createTimestamp,jdbcType=VARCHAR}, ",
        "#{updateTimestamp,jdbcType=VARCHAR})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(Resource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @InsertProvider(type=ResourceSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(Resource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @SelectProvider(type=ResourceSqlProvider.class, method="selectByExample")
    @ConstructorArgs({
        @Arg(column="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Arg(column="resource_name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="resource_code", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="status", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Arg(column="create_timestamp", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="update_timestamp", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    List<Resource> selectByExample(ResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @Select({
        "select",
        "id, resource_name, resource_code, status, create_timestamp, update_timestamp",
        "from resource",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ConstructorArgs({
        @Arg(column="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Arg(column="resource_name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="resource_code", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="status", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Arg(column="create_timestamp", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="update_timestamp", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    Resource selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @UpdateProvider(type=ResourceSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Resource record, @Param("example") ResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @UpdateProvider(type=ResourceSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Resource record, @Param("example") ResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @UpdateProvider(type=ResourceSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Resource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @Update({
        "update resource",
        "set resource_name = #{resourceName,jdbcType=VARCHAR},",
          "resource_code = #{resourceCode,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=INTEGER},",
          "create_timestamp = #{createTimestamp,jdbcType=VARCHAR},",
          "update_timestamp = #{updateTimestamp,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Resource record);
}