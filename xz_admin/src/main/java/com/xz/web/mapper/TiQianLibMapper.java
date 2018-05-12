package com.xz.web.mapper;

import com.xz.web.entity.TiQianLib;
import com.xz.web.entity.TiQianLibExample;
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

public interface TiQianLibMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ti_qian_lib
     *
     * @mbggenerated Sat May 12 18:17:19 CST 2018
     */
    @SelectProvider(type=TiQianLibSqlProvider.class, method="countByExample")
    int countByExample(TiQianLibExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ti_qian_lib
     *
     * @mbggenerated Sat May 12 18:17:19 CST 2018
     */
    @DeleteProvider(type=TiQianLibSqlProvider.class, method="deleteByExample")
    int deleteByExample(TiQianLibExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ti_qian_lib
     *
     * @mbggenerated Sat May 12 18:17:19 CST 2018
     */
    @Delete({
        "delete from ti_qian_lib",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ti_qian_lib
     *
     * @mbggenerated Sat May 12 18:17:19 CST 2018
     */
    @Insert({
        "insert into ti_qian_lib (pic, name, ",
        "publish_time, create_timestamp, ",
        "update_timestamp)",
        "values (#{pic,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{publishTime,jdbcType=VARCHAR}, #{createTimestamp,jdbcType=VARCHAR}, ",
        "#{updateTimestamp,jdbcType=VARCHAR})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(TiQianLib record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ti_qian_lib
     *
     * @mbggenerated Sat May 12 18:17:19 CST 2018
     */
    @InsertProvider(type=TiQianLibSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(TiQianLib record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ti_qian_lib
     *
     * @mbggenerated Sat May 12 18:17:19 CST 2018
     */
    @SelectProvider(type=TiQianLibSqlProvider.class, method="selectByExample")
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="pic", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="publish_time", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="create_timestamp", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="update_timestamp", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    List<TiQianLib> selectByExample(TiQianLibExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ti_qian_lib
     *
     * @mbggenerated Sat May 12 18:17:19 CST 2018
     */
    @Select({
        "select",
        "id, pic, name, publish_time, create_timestamp, update_timestamp",
        "from ti_qian_lib",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="pic", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="publish_time", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="create_timestamp", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="update_timestamp", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    TiQianLib selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ti_qian_lib
     *
     * @mbggenerated Sat May 12 18:17:19 CST 2018
     */
    @UpdateProvider(type=TiQianLibSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TiQianLib record, @Param("example") TiQianLibExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ti_qian_lib
     *
     * @mbggenerated Sat May 12 18:17:19 CST 2018
     */
    @UpdateProvider(type=TiQianLibSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TiQianLib record, @Param("example") TiQianLibExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ti_qian_lib
     *
     * @mbggenerated Sat May 12 18:17:19 CST 2018
     */
    @UpdateProvider(type=TiQianLibSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TiQianLib record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ti_qian_lib
     *
     * @mbggenerated Sat May 12 18:17:19 CST 2018
     */
    @Update({
        "update ti_qian_lib",
        "set pic = #{pic,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "publish_time = #{publishTime,jdbcType=VARCHAR},",
          "create_timestamp = #{createTimestamp,jdbcType=VARCHAR},",
          "update_timestamp = #{updateTimestamp,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(TiQianLib record);
}