package com.xz.web.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT_DISTINCT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.xz.web.entity.Example;
import com.xz.web.entity.ExampleExample.Criteria;
import com.xz.web.entity.ExampleExample.Criterion;
import com.xz.web.entity.ExampleExample;
import java.util.List;
import java.util.Map;

public class ExampleSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public String countByExample(ExampleExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("example");
        applyWhere(example, false);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public String deleteByExample(ExampleExample example) {
        BEGIN();
        DELETE_FROM("example");
        applyWhere(example, false);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public String insertSelective(Example record) {
        BEGIN();
        INSERT_INTO("example");
        
        if (record.getUsername() != null) {
            VALUES("username", "#{username,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            VALUES("password", "#{password,jdbcType=VARCHAR}");
        }
        
        if (record.getAge() != null) {
            VALUES("age", "#{age,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            VALUES("status", "#{status,jdbcType=INTEGER}");
        }
        
        if (record.getSex() != null) {
            VALUES("sex", "#{sex,jdbcType=BIT}");
        }
        
        if (record.getBrif() != null) {
            VALUES("brif", "#{brif,jdbcType=VARCHAR}");
        }
        
        if (record.getUrl() != null) {
            VALUES("url", "#{url,jdbcType=VARCHAR}");
        }
        
        if (record.getTitle() != null) {
            VALUES("title", "#{title,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTimestamp() != null) {
            VALUES("create_timestamp", "#{createTimestamp,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateTimestamp() != null) {
            VALUES("update_timestamp", "#{updateTimestamp,jdbcType=VARCHAR}");
        }
        
        if (record.getFile() != null) {
            VALUES("file", "#{file,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public String selectByExample(ExampleExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("id");
        } else {
            SELECT("id");
        }
        SELECT("username");
        SELECT("password");
        SELECT("age");
        SELECT("status");
        SELECT("sex");
        SELECT("brif");
        SELECT("url");
        SELECT("title");
        SELECT("create_timestamp");
        SELECT("update_timestamp");
        SELECT("file");
        FROM("example");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        Example record = (Example) parameter.get("record");
        ExampleExample example = (ExampleExample) parameter.get("example");
        
        BEGIN();
        UPDATE("example");
        
        if (record.getId() != null) {
            SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getUsername() != null) {
            SET("username = #{record.username,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            SET("password = #{record.password,jdbcType=VARCHAR}");
        }
        
        if (record.getAge() != null) {
            SET("age = #{record.age,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            SET("status = #{record.status,jdbcType=INTEGER}");
        }
        
        if (record.getSex() != null) {
            SET("sex = #{record.sex,jdbcType=BIT}");
        }
        
        if (record.getBrif() != null) {
            SET("brif = #{record.brif,jdbcType=VARCHAR}");
        }
        
        if (record.getUrl() != null) {
            SET("url = #{record.url,jdbcType=VARCHAR}");
        }
        
        if (record.getTitle() != null) {
            SET("title = #{record.title,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTimestamp() != null) {
            SET("create_timestamp = #{record.createTimestamp,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateTimestamp() != null) {
            SET("update_timestamp = #{record.updateTimestamp,jdbcType=VARCHAR}");
        }
        
        if (record.getFile() != null) {
            SET("file = #{record.file,jdbcType=VARCHAR}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("example");
        
        SET("id = #{record.id,jdbcType=INTEGER}");
        SET("username = #{record.username,jdbcType=VARCHAR}");
        SET("password = #{record.password,jdbcType=VARCHAR}");
        SET("age = #{record.age,jdbcType=INTEGER}");
        SET("status = #{record.status,jdbcType=INTEGER}");
        SET("sex = #{record.sex,jdbcType=BIT}");
        SET("brif = #{record.brif,jdbcType=VARCHAR}");
        SET("url = #{record.url,jdbcType=VARCHAR}");
        SET("title = #{record.title,jdbcType=VARCHAR}");
        SET("create_timestamp = #{record.createTimestamp,jdbcType=VARCHAR}");
        SET("update_timestamp = #{record.updateTimestamp,jdbcType=VARCHAR}");
        SET("file = #{record.file,jdbcType=VARCHAR}");
        
        ExampleExample example = (ExampleExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public String updateByPrimaryKeySelective(Example record) {
        BEGIN();
        UPDATE("example");
        
        if (record.getUsername() != null) {
            SET("username = #{username,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            SET("password = #{password,jdbcType=VARCHAR}");
        }
        
        if (record.getAge() != null) {
            SET("age = #{age,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            SET("status = #{status,jdbcType=INTEGER}");
        }
        
        if (record.getSex() != null) {
            SET("sex = #{sex,jdbcType=BIT}");
        }
        
        if (record.getBrif() != null) {
            SET("brif = #{brif,jdbcType=VARCHAR}");
        }
        
        if (record.getUrl() != null) {
            SET("url = #{url,jdbcType=VARCHAR}");
        }
        
        if (record.getTitle() != null) {
            SET("title = #{title,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTimestamp() != null) {
            SET("create_timestamp = #{createTimestamp,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateTimestamp() != null) {
            SET("update_timestamp = #{updateTimestamp,jdbcType=VARCHAR}");
        }
        
        if (record.getFile() != null) {
            SET("file = #{file,jdbcType=VARCHAR}");
        }
        
        WHERE("id = #{id,jdbcType=INTEGER}");
        
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table example
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    protected void applyWhere(ExampleExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            WHERE(sb.toString());
        }
    }
}