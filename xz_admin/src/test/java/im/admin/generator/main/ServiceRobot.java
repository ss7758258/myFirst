package im.admin.generator.main;


import im.admin.generator.bean.TableBean;
import im.admin.generator.db.tools.EntityGen;
import im.admin.generator.factory.ServiceFactory;
import com.xz.web.utils.string.StringUtil;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.List;

public class ServiceRobot {
    public static final String CONFIG_FILE = "classpath*:applicationContext.xml";
    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.setValidating(false);
        context.load(CONFIG_FILE);
        context.refresh();
        ServiceFactory serviceFactory = (ServiceFactory)context.getBean(StringUtil.getLowerStart(ServiceFactory.class.getSimpleName()));
        EntityGen entityGen = (EntityGen)context.getBean(StringUtil.getLowerStart(EntityGen.class.getSimpleName()));
        List<TableBean> list = entityGen.getTableDesc();
        serviceFactory.start(list);
    }
}
