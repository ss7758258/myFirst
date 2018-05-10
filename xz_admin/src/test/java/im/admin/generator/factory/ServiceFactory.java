package im.admin.generator.factory;


import im.admin.generator.bean.TableBean;
import im.admin.generator.constant.Constant;
import im.admin.generator.service.ServiceGenerator;
import im.admin.generator.utils.files.FileUtil;
import im.admin.generator.utils.string.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceFactory implements ApplicationContextAware {
    private Logger logger = Logger.getLogger(ServiceFactory.class);
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
    @Value("${java.base.package}")
    private String basePackage;
    @Value("${java.bos.package}")
    private String bosPackage;
    @Value("${java.beans.package}")
    private String beansPackage;

    public void start(List<TableBean> beanlist) {
        //初始化变量已经文件夹
        String path = HtmlFactory.class.getResource(ROOT).getPath();
        javaSourcePath = project+JAVA_SOURCE_PATH;
        resourcePath = project+RESOURCE_PATH;
        //创建文件夹
        generateFolders();
        //生成Bo，暂时不考虑Bo层
        generateBo(beanlist);
        //生成Service
        ServiceGenerator serviceGenerator = (ServiceGenerator)applicationContext.getBean(StringUtil.getLowerStart(ServiceGenerator.class.getSimpleName()));
        serviceGenerator.generateService(beanlist,javaSourcePath);
    }

    private void generateService(List<TableBean> beanlist) {
    }

    private void generateFolders() {
        String servicesFolder[] = servicesPackage.split("\\.");
        String controllersFolder[] = controllersPackage.split("\\.");
        String bosFolder[] = bosPackage.split("\\.");
        String folderName = javaSourcePath;
        for(int i=0;i<servicesFolder.length;i++)
        {
            folderName = folderName + "/" +servicesFolder[i];
            FileUtil.createFolder(folderName);
        }
        folderName = javaSourcePath;
        for(int i=0;i<controllersFolder.length;i++)
        {
            folderName = folderName + "/" +controllersFolder[i];
            FileUtil.createFolder(folderName);
        }
        folderName = javaSourcePath;
        for(int i=0;i<bosFolder.length;i++)
        {
            folderName = folderName + "/" +bosFolder[i];
            FileUtil.createFolder(folderName);
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
