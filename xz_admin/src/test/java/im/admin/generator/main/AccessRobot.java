package im.admin.generator.main;


import im.admin.generator.db.tools.EntityGen;
import im.admin.generator.factory.AccessFactory;
import com.xz.web.utils.string.StringUtil;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AccessRobot {
    public static final String CONFIG_FILE = "classpath*:applicationContext.xml";
    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.setValidating(false);
        context.load(CONFIG_FILE);
        context.refresh();
        AccessFactory accessFactory = (AccessFactory)context.getBean(StringUtil.getLowerStart(AccessFactory.class.getSimpleName()));
        EntityGen entityGen = (EntityGen)context.getBean(StringUtil.getLowerStart(EntityGen.class.getSimpleName()));
        accessFactory.start(entityGen);
    }
}
