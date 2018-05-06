package com.xz.framework.common.base;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class BaseSqlProvider<T> {

    public String maxByExample(T example) {
        BEGIN();
        SELECT("count(*)");
        FROM("pet");
        return SQL();
    }
}
