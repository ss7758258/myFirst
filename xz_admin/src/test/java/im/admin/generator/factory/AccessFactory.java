package im.admin.generator.factory;


import im.admin.generator.bean.TableBean;
import im.admin.generator.constant.Constant;
import im.admin.generator.db.tools.EntityGen;
import im.admin.generator.db.tools.TableDescBO;
import im.admin.generator.utils.date.DateUtil;
import im.admin.generator.utils.string.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccessFactory implements ApplicationContextAware {
    private Logger logger = Logger.getLogger(AccessFactory.class);
    public static final String CONFIG_FILE = "classpath*:applicationContext.xml";

    private static final String ROOT = Constant.ROOT;
    private static final String JAVA_SOURCE_PATH = Constant.JAVA_SOURCE_PATH;
    private static final String RESOURCE_PATH = Constant.RESOURCE_PATH;
    private String javaSourcePath = "";
    private String resourcePath = "";;

    @Value("${java.project}")
    private String project;
    @Value("${java.services.package}")
    private String servicesPackage;
    @Value("${java.controllers.package}")
    private String controllersPackage;
    @Value("${java.bos.package}")
    private String bosPackage;
    @Value("${java.beans.package}")
    private String beansPackage;

    public void start(EntityGen entityGen) {
        List<TableBean> beanlist = entityGen.getTableDesc();
        entityGen.exeSql("delete from admin;");
        entityGen.exeSql("delete from access;");
        entityGen.exeSql("delete from resource;");
        //初始化管理员
        entityGen.exeSql("insert into admin(username,password,status,sex,title,create_timestamp,update_timestamp) values('admin','admin',1,1,'系统管理员','"+ DateUtil.getCurrentTimestamp()+"','"+ DateUtil.getCurrentTimestamp()+"');");
        int adminId = entityGen.getIdBySql("select * from admin order by id desc;");
        for(int i=0;i<beanlist.size();i++)
        {
            TableBean bean = beanlist.get(i);
            List<TableDescBO> list = bean.getTableDesc();
            for(int j=0;j<list.size();j++)
            {
                TableDescBO bo = list.get(j);
                String resourceName = bean.getTableName();
                String resourceCode = bo.getMemo();
                int resourceid=0;
                if("ID".equalsIgnoreCase(bo.getField())) {
                    entityGen.exeSql("insert into resource(resource_name,resource_code,status,create_timestamp,update_timestamp) values('"+StringUtil.getLowerStart(resourceName)+"','"+resourceCode+"',1,'"+ DateUtil.getCurrentTimestamp()+"','"+ DateUtil.getCurrentTimestamp()+"');");
                    resourceid = entityGen.getIdBySql("select * from resource order by id desc;");
                    entityGen.exeSql("insert into access(resource_id,resource_name,resource_code,status,userid,username,add_flag,del_flag,upd_flag,view_flag,create_timestamp,update_timestamp) " +
                            "values("+resourceid+",'"+ StringUtil.getLowerStart(resourceName)+"','"+resourceCode+"',1,"+adminId+",'admin',true,true,true,true,'"+ DateUtil.getCurrentTimestamp()+"','"+ DateUtil.getCurrentTimestamp()+"');");
                }
            }
        }
    }


    private void generateBo(List<TableBean> beanlist) {

    }

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
