package im.admin.generator.db.tools;

import im.admin.generator.bean.TableBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * DE数据插入工具
 */
@Component
public class EntityGen implements ApplicationContextAware {

    private Logger logger = Logger.getLogger(EntityGen.class);

    @Value("${jdbc.schema}")
    private String schema;
    @Value("${jdbc.driverClass}")
    private String driverClass;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.userId}")
    private String userId;
    @Value("${jdbc.password}")
    private String password;

    public DataBaseBO getDataBaseBO() {
        DataBaseBO dataBaseBO = new DataBaseBO();
        dataBaseBO.setUrl(url);
        dataBaseBO.setDriver(driverClass);
        dataBaseBO.setUserName(userId);
        dataBaseBO.setPasswrod(password);
        dataBaseBO.setTableSchema("'"+schema+"'");
        return dataBaseBO;
    }

    public EntityGen() {
        super();
    }
    public List<String> getTableList(DataBaseBO dataBaseBO) {
        List<String> list = new ArrayList<String>();
        try {
            Class.forName(dataBaseBO.getDriver());
            Connection conn = DriverManager.getConnection(dataBaseBO.getUrl(),
                    dataBaseBO.getUserName(), dataBaseBO.getPasswrod());
            PreparedStatement ps = conn.prepareStatement(dataBaseBO.getSql());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1).toString());
            }
            close(rs, ps, conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean exeSql(String sql) {
        System.out.println(sql);
        List<TableDescBO> list = new ArrayList<TableDescBO>();
        TableDescBO tableDescBO = null;
        boolean flag = false;
        DataBaseBO dataBaseBO = this.getDataBaseBO();
        try {
            Class.forName(dataBaseBO.getDriver());
            Connection conn = DriverManager.getConnection(dataBaseBO.getUrl(),
                    dataBaseBO.getUserName(), dataBaseBO.getPasswrod());
            PreparedStatement ps = conn.prepareStatement(sql);
            flag = ps.execute();
            close(null, ps, conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public int getIdBySql(String sql) {
        List<TableDescBO> list = new ArrayList<TableDescBO>();
        TableDescBO tableDescBO = null;
        DataBaseBO dataBaseBO = this.getDataBaseBO();
        try {
            Class.forName(dataBaseBO.getDriver());
            Connection conn = DriverManager.getConnection(dataBaseBO.getUrl(),
                    dataBaseBO.getUserName(), dataBaseBO.getPasswrod());
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs =  ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("id");
            }
            close(null, ps, conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 数据库表结构情况
     *
     * @param dataBaseBO
     *            数据库配置信息
     * @return 所需查询的数据表的字段信息
     */
    public List<TableDescBO> getTableDescBOList(DataBaseBO dataBaseBO) {
        List<TableDescBO> list = new ArrayList<TableDescBO>();
        TableDescBO tableDescBO = null;
        try {
            Class.forName(dataBaseBO.getDriver());
            Connection conn = DriverManager.getConnection(dataBaseBO.getUrl(),
                    dataBaseBO.getUserName(), dataBaseBO.getPasswrod());
            PreparedStatement ps = conn.prepareStatement(dataBaseBO.getSql());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tableDescBO = new TableDescBO();
                tableDescBO.setField(rs.getString(1));
                tableDescBO.setType(rs.getString(2));
                tableDescBO.setMemo(rs.getString(3));
                tableDescBO.setMunericLength(rs.getString(4));
                tableDescBO.setNumericScale(rs.getString(5));
                tableDescBO.setIsNullable(rs.getString(6));
                tableDescBO.setExtra(rs.getString(7));
                tableDescBO.setIsDefault(rs.getString(8));
                tableDescBO.setCharacterLength(rs.getString(9));
                list.add(tableDescBO);
            }
            close(rs, ps, conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 执行向数据库表:<code>data_element_config</code>中插入数据
     *
     * @param dataBaseBO
     *            数据库配置信息
     * @param decBo
     *            data_element_config这张表的BO类
     * @return 返回:<code>-1</code>, 表示插入数据失败，否则成功
     */
    public int insertIntoDECTable(DataBaseBO dataBaseBO,
                                  DataElementConfigBO decBo) {
        int result = -1;
        if (decBo != null) {
            String sql = decBo.getInsertIntoSQL() + decBo.getDeName() + ","
                    + decBo.getDeGroup() + "," + decBo.getMemo() + ","
                    + decBo.getDataType() + "," + decBo.getValueCheck() + ","
                    + decBo.getYxBj() + ")";
            try {
                Class.forName(dataBaseBO.getDriver());
                Connection conn = DriverManager.getConnection(
                        dataBaseBO.getUrl(), dataBaseBO.getUserName(),
                        dataBaseBO.getPasswrod());
                PreparedStatement ps = conn.prepareStatement(sql);
                result = ps.executeUpdate();
                close(null, ps, conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public List<TableBean> getTableDesc() {
        List<TableBean> list = new ArrayList<TableBean>();
        DataBaseBO dataBaseBO = this.getDataBaseBO();
        dataBaseBO.setSql(dataBaseBO.getSelectTableSQL() + dataBaseBO.getTableSchema());
        List<String> tables = this.getTableList(dataBaseBO);
        if (tables != null) {
            for (String table : tables) {
                TableBean bean = new TableBean();
                dataBaseBO.setTableName("'"+table.toString()+"'");
                dataBaseBO.setSql(dataBaseBO.getSelectTableDescSQL() + dataBaseBO.getTableName());
                List<TableDescBO> bolist = this.getTableDescBOList(dataBaseBO);
                bean.setTableName(table.toString());
                bean.setTableDesc(bolist);
                list.add(bean);
            }
        }
        return list;
    }
    /**
     * 去除括号，如："int(11)",去除括号了以后，为："int"
     *
     * @param oldType
     * @return
     */
    public static String getType(String oldType) {
        if (oldType != null && !oldType.equals("")) {
            return oldType.substring(0, oldType.indexOf("("));
        }
        return null;
    }
    /**
     * 对数据库表描述进行封装成DataElementConfigBO对象
     *
     * @param tableDescBO
     *            数据库表的描述
     * @param group
     *            字段的分组名称，在表：<code>data_element_config</code>中对应的
     *            <code>de_group</code>字段
     * @return dataElementConfig对象的一个实例
     */
    public DataElementConfigBO getDataElementConfigBO(TableDescBO tableDescBO,
                                                      String group) {
        DataElementConfigBO bo = null;
        if (tableDescBO != null) {
            bo = new DataElementConfigBO();
            bo.setDeName("'" + tableDescBO.getField() + "'");
            bo.setDeGroup("'" + group + "'");
            bo.setValueCheck("'true'");
            bo.setYxBj("'1'");
            bo.setMemo("'" + tableDescBO.getMemo() + "'");
            bo.setDataType(1);
        }
        return bo;
    }
    /**
     * 关闭数据库的相关链接
     *
     * @param rs
     *            记录集
     * @param ps
     *            声明
     * @param conn
     *            链接对象
     */
    public void close(ResultSet rs, PreparedStatement ps, Connection conn) {
        // 关闭记录集
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 关闭声明
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 关闭链接对象
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}