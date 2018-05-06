package im.admin.generator.bean;

import im.admin.generator.db.tools.TableDescBO;

import java.util.List;

public class TableBean {
    private String tableName;
    private List<TableDescBO> tableDesc;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<TableDescBO> getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(List<TableDescBO> tableDesc) {
        this.tableDesc = tableDesc;
    }
}
