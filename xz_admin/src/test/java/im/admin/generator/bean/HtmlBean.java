package im.admin.generator.bean;

import im.admin.generator.db.tools.TableDescBO;
import im.admin.generator.enums.BeanEnum;
import com.xz.web.utils.string.StringUtil;

public class HtmlBean {
    private String title;
    private String header;
    private String copyright;
    private String tableName;
    private String tableMem;
    private TableBean tableBean;
    private String addForm="";
    private String editForm="";
    private String menuForm="";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public TableBean getTableBean() {
        return tableBean;
    }

    public void setTableBean(TableBean tableBean) {
        this.tableBean = tableBean;
    }

    public void buildTableBean(TableBean tableBean) {
        this.tableBean = tableBean;
        for(int j=0;j<tableBean.getTableDesc().size();j++)
        {
            TableDescBO bo = tableBean.getTableDesc().get(j);
            String memoStr = bo.getMemo();
            String memo[] = memoStr.split("-");
            if (bo.getField().equalsIgnoreCase("create_timestamp")||bo.getField().equalsIgnoreCase("update_timestamp")) {
                continue;
            }
            else if (bo.getField().equalsIgnoreCase("ID")) {
                editForm = editForm + "<input id=\""+ StringUtil.getLowerStart(bo.getField()) +"\" type=\"hidden\">";
            }
            else if(BeanEnum.VARCHAR.getValue().equalsIgnoreCase(memo[0]))
            {
                addForm = addForm + "<div class=\"form-group\">";
                addForm = addForm + "<label class=\"col-sm-2 control-label\">"+memo[1]+"</label>";
                addForm = addForm + "<div class=\"col-sm-10\"><input id=\""+ StringUtil.getLowerStart(bo.getField()) +"\" type=\"text\" class=\"form-control\" maxlength=\""+bo.getCharacterLength()+"\" placeholder=\"请输入"+memo[1]+"\"></div>";
                addForm = addForm + "</div>";
                addForm = addForm + "<div class=\"hr-line-dashed\"></div>\n";
                //
                editForm = editForm + "<div class=\"form-group\">";
                editForm = editForm + "<label class=\"col-sm-2 control-label\">"+memo[1]+"</label>";
                editForm = editForm + "<div class=\"col-sm-10\"><input id=\""+ StringUtil.getLowerStart(bo.getField()) +"\" type=\"text\" class=\"form-control\" maxlength=\""+bo.getCharacterLength()+"\" placeholder=\"请输入"+memo[1]+"\"></div>";
                editForm = editForm + "</div>";
                editForm = editForm + "<div class=\"hr-line-dashed\"></div>\n";
            }
            else if(BeanEnum.INT.getValue().equalsIgnoreCase(memo[0]))
            {
                addForm = addForm + "<div class=\"form-group\">";
                addForm = addForm + "<label class=\"col-sm-2 control-label\">"+memo[1]+"</label>";
                addForm = addForm + "<div class=\"col-sm-10\"><input id=\""+ StringUtil.getLowerStart(bo.getField()) +"\" type=\"text\" class=\"form-control\" maxlength=\""+bo.getMunericLength()+"\" placeholder=\"请输入"+memo[1]+"\"></div>";
                addForm = addForm + "</div>";
                addForm = addForm + "<div class=\"hr-line-dashed\"></div>\n";
                //
                editForm = editForm + "<div class=\"form-group\">";
                editForm = editForm + "<label class=\"col-sm-2 control-label\">"+memo[1]+"</label>";
                editForm = editForm + "<div class=\"col-sm-10\"><input id=\""+ StringUtil.getLowerStart(bo.getField()) +"\" type=\"text\" class=\"form-control\" maxlength=\""+bo.getMunericLength()+"\" placeholder=\"请输入"+memo[1]+"\"></div>";
                editForm = editForm + "</div>";
                editForm = editForm + "<div class=\"hr-line-dashed\"></div>\n";
            }
            else if(BeanEnum.SELECT_INT.getValue().equalsIgnoreCase(memo[0])||BeanEnum.SELECT_STRING.getValue().equalsIgnoreCase(memo[0]))
            {
                String value = memo[2];
                String values[] = value.split(",");
                if(values.length<=1) continue;
                addForm = addForm + "<div class=\"form-group\">";
                addForm = addForm + "<label class=\"col-sm-2 control-label\">"+memo[1]+"</label>";
                addForm = addForm + "<div class=\"col-sm-10\"><select class=\"form-control m-b\" id=\"" + StringUtil.getLowerStart(bo.getField()) + "\">";
                editForm = editForm + "<div class=\"form-group\">";
                editForm = editForm + "<label class=\"col-sm-2 control-label\">"+memo[1]+"</label>";
                editForm = editForm + "<div class=\"col-sm-10\"><select class=\"form-control m-b\" id=\"" + StringUtil.getLowerStart(bo.getField()) + "\">";
                for(int k =0;k<values.length;k++) {
                    String v = values[k];
                    String vs[] = v.split("=");
                    addForm = addForm + "<option value=\""+vs[0]+"\">"+vs[1]+"</option>";
                    editForm = editForm + "<option value=\""+vs[0]+"\">"+vs[1]+"</option>";
                }
                addForm = addForm + "</select></div>";
                addForm = addForm + "</div>";
                addForm = addForm + "<div class=\"hr-line-dashed\"></div>\n";
                editForm = editForm + "</select></div>";
                editForm = editForm + "</div>";
                editForm = editForm + "<div class=\"hr-line-dashed\"></div>\n";
            }
            else if(BeanEnum.RADIO_BOOLEAN.getValue().equalsIgnoreCase(memo[0]))
            {
                String value = memo[2];
                String values[] = value.split(",");
                if(values.length<=1) continue;
                addForm = addForm + "<div class=\"form-group\">";
                addForm = addForm + "<label class=\"col-sm-2 control-label\">"+memo[1]+"</label>";
                addForm = addForm + "<div class=\"col-sm-10\">";
                addForm = addForm + "<div class=\"i-checks\"><label> <input type=\"radio\" checked value=\"true\" name=\"" + StringUtil.getLowerStart(bo.getField()) + "\"><i></i> "+values[0]+" </label></div>";
                addForm = addForm + "<div class=\"i-checks\"><label> <input type=\"radio\" value=\"false\" name=\"" + StringUtil.getLowerStart(bo.getField()) + "\"><i></i> "+values[1]+" </label></div>";
                addForm = addForm + "</div>";
                addForm = addForm + "</div>";
                addForm = addForm + "<div class=\"hr-line-dashed\"></div>\n";
                //

                editForm = editForm + "<div class=\"form-group\">";
                editForm = editForm + "<label class=\"col-sm-2 control-label\">"+memo[1]+"</label>";
                editForm = editForm + "<div class=\"col-sm-10\">";
                editForm = editForm + "<div class=\"i-checks\"><label> <input type=\"radio\" checked value=\"true\" name=\"" + StringUtil.getLowerStart(bo.getField()) + "\"><i></i> "+values[0]+" </label></div>";
                editForm = editForm + "<div class=\"i-checks\"><label> <input type=\"radio\" value=\"false\" name=\"" + StringUtil.getLowerStart(bo.getField()) + "\"><i></i> "+values[1]+" </label></div>";
                editForm = editForm + "</div>";
                editForm = editForm + "</div>";
                editForm = editForm + "<div class=\"hr-line-dashed\"></div>\n";
            }else if(BeanEnum.FILE.getValue().equalsIgnoreCase(memo[0]))
            {
                addForm = addForm + "<div class=\"form-group\">";
                addForm = addForm + "<label class=\"col-sm-2 control-label\">"+memo[1]+"</label>";
                addForm = addForm + "<div class=\"col-sm-3\"><input id=\"" + StringUtil.getLowerStart(bo.getField()) + "\" name=\"file\" type=\"file\" class=\"form-control\" maxlength=\""+bo.getCharacterLength()+"\"><span class=\"help-block m-b-none\" id=\""+ StringUtil.getLowerStart(bo.getField()) +"Span\"></span></div>";
                addForm = addForm + "<div class=\"col-sm-4 col-sm-offset-2\">";
                addForm = addForm + "<button class=\"btn btn-primary\" onclick=\"upload('" + StringUtil.getLowerStart(bo.getField()) + "','" + StringUtil.getLowerStart(tableBean.getTableName()) + "')\">上传</button>";
                addForm = addForm + "</div>";
                addForm = addForm + "</div>";
                addForm = addForm + "<div class=\"hr-line-dashed\"></div>\n";
                //
                editForm = editForm + "<div class=\"form-group\">";
                editForm = editForm + "<label class=\"col-sm-2 control-label\">"+memo[1]+"</label>";
                editForm = editForm + "<div class=\"col-sm-3\"><input id=\"" + StringUtil.getLowerStart(bo.getField()) + "\" name=\"file\" type=\"file\" class=\"form-control\" maxlength=\""+bo.getCharacterLength()+"\"><span class=\"help-block m-b-none\" id=\""+ StringUtil.getLowerStart(bo.getField()) +"Span\"></span></div>";
                editForm = editForm + "<div class=\"col-sm-4 col-sm-offset-2\">";
                editForm = editForm + "<button class=\"btn btn-primary\" onclick=\"upload('" + StringUtil.getLowerStart(bo.getField()) + "','" + StringUtil.getLowerStart(tableBean.getTableName()) + "')\">上传</button>";
                editForm = editForm + "</div>";
                editForm = editForm + "</div>";
                editForm = editForm + "<div class=\"hr-line-dashed\"></div>\n";
            }else if(BeanEnum.IMAGE.getValue().equalsIgnoreCase(memo[0]))
            {
                addForm = addForm + "<div class=\"form-group\">";
                addForm = addForm + "<label class=\"col-sm-2 control-label\">"+memo[1]+"</label>";
                addForm = addForm + "<div class=\"col-sm-3\"><input id=\"" + StringUtil.getLowerStart(bo.getField()) + "\" name=\"file\" type=\"file\" class=\"form-control\" maxlength=\""+bo.getCharacterLength()+"\"><span class=\"help-block m-b-none\" id=\""+ StringUtil.getLowerStart(bo.getField()) +"Span\"></span></div>";
                addForm = addForm + "<div class=\"col-sm-4 col-sm-offset-2\">";
                addForm = addForm + "<button class=\"btn btn-primary\" onclick=\"upload('" + StringUtil.getLowerStart(bo.getField()) + "','" + StringUtil.getLowerStart(tableBean.getTableName()) + "')\">上传</button>";
                addForm = addForm + "</div>";
                addForm = addForm + "</div>";
                addForm = addForm + "<div class=\"hr-line-dashed\"></div>\n";
                //
                editForm = editForm + "<div class=\"form-group\">";
                editForm = editForm + "<label class=\"col-sm-2 control-label\">"+memo[1]+"</label>";
                editForm = editForm + "<div class=\"col-sm-3\"><input id=\"" + StringUtil.getLowerStart(bo.getField()) + "\" name=\"file\" type=\"file\" class=\"form-control\" maxlength=\""+bo.getCharacterLength()+"\"><span class=\"help-block m-b-none\" id=\""+ StringUtil.getLowerStart(bo.getField()) +"Span\"></span></div>";
                editForm = editForm + "<div class=\"col-sm-4 col-sm-offset-2\">";
                editForm = editForm + "<button class=\"btn btn-primary\" onclick=\"upload('" + StringUtil.getLowerStart(bo.getField()) + "','" + StringUtil.getLowerStart(tableBean.getTableName()) + "')\">上传</button>";
                editForm = editForm + "</div>";
                editForm = editForm + "</div>";
                editForm = editForm + "<div class=\"hr-line-dashed\"></div>\n";
            }else
            {
                addForm = addForm + "<div class=\"form-group\">";
                addForm = addForm + "<label class=\"col-sm-2 control-label\">"+memo[1]+"</label>";
                addForm = addForm + "<div class=\"col-sm-10\"><input id=\""+ StringUtil.getLowerStart(bo.getField()) +"\" type=\"text\" class=\"form-control\" maxlength=\""+bo.getCharacterLength()+"\" placeholder=\"请输入"+memo[1]+"\"></div>";
                addForm = addForm + "</div>";
                addForm = addForm + "<div class=\"hr-line-dashed\"></div>\n";
                //
                editForm = editForm + "<div class=\"form-group\">";
                editForm = editForm + "<label class=\"col-sm-2 control-label\">"+memo[1]+"</label>";
                editForm = editForm + "<div class=\"col-sm-10\"><input id=\""+ StringUtil.getLowerStart(bo.getField()) +"\" type=\"text\" class=\"form-control\" maxlength=\""+bo.getCharacterLength()+"\" placeholder=\"请输入"+memo[1]+"\"></div>";
                editForm = editForm + "</div>";
                editForm = editForm + "<div class=\"hr-line-dashed\"></div>\n";
            }
        }
    }

    public String getTableMem() {
        return tableMem;
    }

    public void setTableMem(String tableMem) {
        this.tableMem = tableMem;
    }

    public String getAddForm() {
        return addForm;
    }

    public void setAddForm(String addForm) {
        this.addForm = addForm;
    }

    public String getEditForm() {
        return editForm;
    }

    public void setEditForm(String editForm) {
        this.editForm = editForm;
    }

    public String getMenuForm() {
        return menuForm;
    }

    public void setMenuForm(String menuForm) {
        this.menuForm = menuForm;
    }
}
