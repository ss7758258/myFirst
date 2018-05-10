package im.admin.generator.main;


import im.admin.generator.bean.HtmlBean;
import im.admin.generator.bean.TableBean;
import im.admin.generator.constant.Constant;
import im.admin.generator.db.tools.EntityGen;
import im.admin.generator.db.tools.TableDescBO;
import im.admin.generator.factory.HtmlFactory;
import im.admin.generator.utils.string.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.List;

public class HtmlRobot {
    private static Logger logger = Logger.getLogger(HtmlRobot.class);
    public static final String CONFIG_FILE = "classpath*:applicationContext.xml";
    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.setValidating(false);
        context.load(CONFIG_FILE);
        context.refresh();
        EntityGen entityGen = (EntityGen)context.getBean(StringUtil.getLowerStart(EntityGen.class.getSimpleName()));
        List<TableBean> list = entityGen.getTableDesc();
        String menuForm="";
        menuForm = menuForm +"<li>";
        menuForm = menuForm +"<a href=\"#\"><i class=\"fa fa-th-large\"></i>";
        menuForm = menuForm +"<span class=\"nav-label\">菜单</span> <span class=\"fa arrow\"></span>";
        menuForm = menuForm +"</a>";
        menuForm = menuForm +"<ul class=\"nav nav-second-level collapse\">";
        for(int i=0;i<list.size();i++)
        {
            TableBean tableBean = list.get(i);
            for(int j=0;j<tableBean.getTableDesc().size();j++)
            {
                TableDescBO bo = tableBean.getTableDesc().get(j);
                if(bo.getField().equalsIgnoreCase("ID")) {
                    menuForm = menuForm +"<li><a href=\""+StringUtil.getLowerStart(tableBean.getTableName())+"List.html\">"+bo.getMemo()+"</a></li>";
                }
            }
        }
        menuForm = menuForm +"</ul>";
        menuForm = menuForm +"</li>";
        for(int i=0;i<list.size();i++)
        {
            TableBean tableBean = list.get(i);
            HtmlFactory factory = new HtmlFactory();
            HtmlBean htmlBean = factory.getHtmlBean();
            for(int j=0;j<tableBean.getTableDesc().size();j++)
            {
                TableDescBO bo = tableBean.getTableDesc().get(j);
                if(bo.getField().equalsIgnoreCase("ID")) {
                    htmlBean.setTitle(bo.getMemo());
                    htmlBean.setTableMem(bo.getMemo());
                }
            }
            htmlBean.setCopyright(Constant.COPYRIGHT);
            htmlBean.setTableName(tableBean.getTableName());
            htmlBean.setMenuForm(menuForm);
            htmlBean.buildTableBean(tableBean);
            factory.outputJS(htmlBean);
            factory.generateHtml(htmlBean);
            //logger.info(htmlBean.getAddForm());
        }
    }
}
