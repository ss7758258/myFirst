package com.xz.web.mapper;

import com.xz.web.entity.Example;
import com.xz.web.entity.ExampleExample;
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

public interface ExampleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @SelectProvider(type=ExampleSqlProvider.class, method="countByExample")
    int countByExample(ExampleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @DeleteProvider(type=ExampleSqlProvider.class, method="deleteByExample")
    int deleteByExample(ExampleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @Delete({
        "delete from example",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @Insert({
        "insert into example (username, password, ",
        "age, status, sex, ",
        "brif, url, title, ",
        "create_timestamp, update_timestamp, ",
        "file)",
        "values (#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{age,jdbcType=INTEGER}, #{status,jdbcType=INTEGER}, #{sex,jdbcType=BIT}, ",
        "#{brif,jdbcType=VARCHAR}, #{url,jdbcType=VARCHAR}, #{title,jdbcType=VARCHAR}, ",
        "#{createTimestamp,jdbcType=VARCHAR}, #{updateTimestamp,jdbcType=VARCHAR}, ",
        "#{file,jdbcType=VARCHAR})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(Example record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @InsertProvider(type=ExampleSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(Example record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @SelectProvider(type=ExampleSqlProvider.class, method="selectByExample")
    @ConstructorArgs({
        @Arg(column="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Arg(column="username", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="password", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="age", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Arg(column="status", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Arg(column="sex", javaType=Boolean.class, jdbcType=JdbcType.BIT),
        @Arg(column="brif", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="url", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="title", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="create_timestamp", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="update_timestamp", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="file", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    List<Example> selectByExample(ExampleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @Select({
        "select",
        "id, username, password, age, status, sex, brif, url, title, create_timestamp, ",
        "update_timestamp, file",
        "from example",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ConstructorArgs({
        @Arg(column="id", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Arg(column="username", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="password", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="age", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Arg(column="status", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Arg(column="sex", javaType=Boolean.class, jdbcType=JdbcType.BIT),
        @Arg(column="brif", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="url", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="title", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="create_timestamp", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="update_timestamp", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="file", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    Example selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @UpdateProvider(type=ExampleSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Example record, @Param("example") ExampleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @UpdateProvider(type=ExampleSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Example record, @Param("example") ExampleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @UpdateProvider(type=ExampleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Example record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    @Update({
        "update example",
        "set username = #{username,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "age = #{age,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "sex = #{sex,jdbcType=BIT},",
          "brif = #{brif,jdbcType=VARCHAR},",
          "url = #{url,jdbcType=VARCHAR},",
          "title = #{title,jdbcType=VARCHAR},",
          "create_timestamp = #{createTimestamp,jdbcType=VARCHAR},",
          "update_timestamp = #{updateTimestamp,jdbcType=VARCHAR},",
          "file = #{file,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Example record);
}