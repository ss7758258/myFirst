package im.admin.generator.db.tools;

import java.io.Serializable;

public class DataElementConfigBO implements Serializable {
    /**
     * 数据库表的字段名称：TableDescBO - field
     */
    private String deName;
    /**
     * 数据库表的分组，这里主要是在却别不同的字段名称<br>
     * 如：有同一个字段名为<code>name</code>,那么在生成DE的过程中系统不知道<br>
     * 是哪一个组或者哪一个用例的<code>name</code>字段，，如果一个字段是<code>TEST</code><br>
     * 一个字段是<code>DEMO</code>的，那么在生成DE的时候，就很容易区分了<br>
     * 则分别生成的DE是：<code>DE_TEST_NAME</code>和<code>DE_DEMO_NAME</code><br>
     */
    private String deGroup;
    /**
     * 数据库表字段的描述
     */
    private String memo;
    /**
     * 数据库表字段对应的数据类型
     */
    private int dataType;
    /**
     * 该属性默认为：<code>true</code>,不用去修改
     */
    private String valueCheck;
    /**
     * 有效标记，这里统一设置为:<code>1</code>,表示有效的<br>
     * 如果设置为：<code>0</code>,则在生成DE的时候，该类会被标记为：<code>@Deprecated</code>
     */
    private String yxBj;
    /**
     * 插入数据库表：<code>data_element_config</code>的sql语句
     */
    private String insertIntoSQL = "INSERT INTO DATA_ELEMENT_CONFIG(DE_NAME,DE_GROUP,MEMO,DATA_TYPE,VALUE_CHECK,YX_BJ) VALUES (";
    public String getDeName() {
        return deName;
    }
    public void setDeName(String deName) {
        this.deName = deName;
    }
    public String getDeGroup() {
        return deGroup;
    }
    public void setDeGroup(String deGroup) {
        this.deGroup = deGroup;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public int getDataType() {
        return dataType;
    }
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
    public String getValueCheck() {
        return valueCheck;
    }
    public void setValueCheck(String valueCheck) {
        this.valueCheck = valueCheck;
    }
    public String getYxBj() {
        return yxBj;
    }
    public void setYxBj(String yxBj) {
        this.yxBj = yxBj;
    }
    public String getInsertIntoSQL() {
        return insertIntoSQL;
    }
    public void setInsertIntoSQL(String insertIntoSQL) {
        this.insertIntoSQL = insertIntoSQL;
    }
}