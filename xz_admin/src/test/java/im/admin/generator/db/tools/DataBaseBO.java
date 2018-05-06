package im.admin.generator.db.tools;

import java.io.Serializable;
import java.util.List;

public class DataBaseBO implements Serializable {
    private final String SELECT_SQL_FIELD = " column_name as field,";
    private final String SELECT_SQL_TYPE = " data_type as type,";
    private final String SELECT_SQL_MEMO = " column_comment as memo,";
    private final String SELECT_SQL_MUNERIC_LENGTH = " numeric_precision as munericLength,";
    private final String SELECT_SQL_NUMERIC_SCALE = " numeric_scale as numericScale, ";
    private final String SELECT_SQL_ISNULLABLE = " is_nullable as isNullable,";
    private final String SELECT_SQL_EXTRA = " CASE WHEN extra = 'auto_increment' THEN 1 ELSE 0 END as extra,";
    private final String SELECT_SQL_ISDEFAULT = " column_default as isDefault,";
    private final String SELECT_SQL_CHARACTER_LENGTH = " character_maximum_length  AS characterLength ";
    /**
     * 查询表sql
     */
    private String selectTableSQL = "SELECT TABLE_NAME FROM Information_schema.TABLES where TABLE_SCHEMA=";
    /**
     * 查询表结构sql
     */
    private String selectTableDescSQL = "";
    /**
     * 数据齐全
     */
    private boolean flag = true;
    /**
     * 驱动名称
     */
    private String driver;
    /**
     * 数据库名称
     */
    private String dbName;
    /**
     * 数据库密码
     */
    private String passwrod;
    /**
     * 数据库用户名
     */
    private String userName;
    /**
     * 访问数据库的url
     */
    private String url;
    /**
     * 端口号
     */
    private String port;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 数据类型：mysql， oracle等等
     */
    private String dbType;
    /**
     * 根据sql:show tables;查询出的数据库表名称
     */
    private List<String> tables;
    /**
     * 数据库表名称
     */
    private String tableName;
    /**
     * 数据库名称
     */
    private String tableSchema;
    /**
     * sql语句
     */
    private String sql;
    public String getDriver() {
        return driver;
    }
    public void setDriver(String driver) {
        this.driver = driver;
    }
    public String getDbName() {
        return dbName;
    }
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    public String getPasswrod() {
        return passwrod;
    }
    public void setPasswrod(String passwrod) {
        this.passwrod = passwrod;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getSql() {
        return sql;
    }
    public void setSql(String sql) {
        this.sql = sql;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getDbType() {
        return dbType;
    }
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }
    public List<String> getTables() {
        return tables;
    }
    public void setTables(List<String> tables) {
        this.tables = tables;
    }
    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public String getSelectTableSQL() {
        return selectTableSQL;
    }
    public String getSelectTableDescSQL() {
        selectTableDescSQL="SELECT " + SELECT_SQL_FIELD + SELECT_SQL_TYPE + SELECT_SQL_MEMO + SELECT_SQL_MUNERIC_LENGTH + SELECT_SQL_NUMERIC_SCALE + SELECT_SQL_ISNULLABLE + SELECT_SQL_EXTRA + SELECT_SQL_ISDEFAULT + SELECT_SQL_CHARACTER_LENGTH + " FROM Information_schema.columns WHERE table_schema="+tableSchema+" and table_Name = ";
        return selectTableDescSQL;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public boolean getFlag() {
        return flag;
    }
    public String getTableSchema() {
        return tableSchema;
    }
    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }
}