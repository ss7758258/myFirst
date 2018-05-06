package im.admin.generator.service;

import im.admin.generator.bean.TableBean;
import im.admin.generator.constant.Constant;
import im.admin.generator.db.tools.TableDescBO;
import im.admin.generator.enums.BeanEnum;
import im.admin.generator.factory.HtmlFactory;
import com.xz.web.utils.files.FileUtil;
import com.xz.web.utils.string.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceGenerator  implements ApplicationContextAware {

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
    @Value("${java.mappers.package}")
    private String mappersPackage;
    @Value("${java.base.package}")
    private String basePackage;


    private static final String ROOT = Constant.ROOT;
    private static final String HTML_PATH = Constant.HTML_PATH;
    private static final String TEMPLATE_PATH = Constant.TEMPLATE_PATH;
    private static final String HTML_END = Constant.HTML_END;
    private static final String HTML_START = Constant.HTML_START;
    private static final String BODY = Constant.BODY;
    private static final String HEADER = Constant.HEADER;

    private String javaSourcePath;
    private String servicesPath;
    private String controllerPath;
    private List<TableBean> beanlist;
    private void generateServiceInterface() {
        for(int i=0;i<beanlist.size();i++)
        {
            TableBean bean = beanlist.get(i);
            String tableName = bean.getTableName();
            String className = StringUtil.getUpperStart(tableName)+"Service.java";
            FileUtil.deleteTxtFile(servicesPath+className);
            String outData = generateServiceInterfaceText(bean);
            FileUtil.writeTxtFile(servicesPath+className,outData);
        }
    }

    private String generateServiceInterfaceText(TableBean bean) {
        String entityName = StringUtil.getUpperStart(bean.getTableName());
        String path = HtmlFactory.class.getResource(ROOT).getPath();
        String templateSource = FileUtil.readTxtFile(path+Constant.SERVICE_TEMPALTE);
        String javaSource = "";
        javaSource = javaSource + "package "+servicesPackage+";\n";
        javaSource = javaSource + "import "+beansPackage+"."+entityName+";\n";
        javaSource = javaSource + "import "+basePackage+".PageInfo;\n";
        javaSource = javaSource + "import "+basePackage+".BaseService;\n";
        javaSource = javaSource + "import java.util.List;\n";
        javaSource = javaSource + "\n";
        javaSource = javaSource + templateSource.replaceAll("<Object>",entityName);
        return javaSource;
    }

    private void generateServiceControllerClass() {
        for(int i=0;i<beanlist.size();i++)
        {
            TableBean bean = beanlist.get(i);
            String tableName = beanlist.get(i).getTableName();
            String className = StringUtil.getUpperStart(tableName)+"ServiceImpl.java";
            FileUtil.deleteTxtFile(servicesPath+className);
            String outData = generateServiceClassText(bean);
            FileUtil.writeTxtFile(servicesPath+className,outData);
        }

        for(int i=0;i<beanlist.size();i++)
        {
            TableBean bean = beanlist.get(i);
            String tableName = beanlist.get(i).getTableName();
            String className = StringUtil.getUpperStart(tableName)+"Controller.java";
            FileUtil.deleteTxtFile(controllerPath+className);
            String outData = generateControllerClassText(bean);
            FileUtil.writeTxtFile(controllerPath+className,outData);
        }
    }

    private String generateControllerClassText(TableBean bean) {
        String path = HtmlFactory.class.getResource(ROOT).getPath();
        String templateSource = FileUtil.readTxtFile(path+Constant.CONTROLLER_TEMPALTE);
        String javaSource = "";
        javaSource = javaSource + "package "+controllersPackage+";\n";
        javaSource = javaSource + templateSource;
        javaSource = javaSource.replaceAll("<Object>",StringUtil.getUpperStart(bean.getTableName()));
        javaSource = javaSource.replaceAll("<object>",StringUtil.getLowerStart(bean.getTableName()));
        List<TableDescBO> list = bean.getTableDesc();
        String uploadCode = "@RequestMapping(\"json/<field>Upload\")\n" +
                "    public\n" +
                "    @ResponseBody\n" +
                "    String <field>Upload(@RequestParam(name=\"file\",required=false) MultipartFile <field>) {\n" +
                "        AjaxBean<String> ajaxBean = new AjaxBean<String>();\n" +
                "        if(<field>==null|| StringUtil.isEmpty(<field>.getOriginalFilename()))\n" +
                "        {\n" +
                "            ajaxBean.setStatus(AjaxStatus.ERROR);\n" +
                "            ajaxBean.setMessage(\"文件为空!\");\n" +
                "            return this.ajaxJson(ajaxBean);\n" +
                "        }\n" +
                "        try {\n" +
                "            String file<Field> = FileUtil.uploadFile(\"<object>\", this.getRequest(), <field>);\n" +
                "            ajaxBean.setStatus(AjaxStatus.SUCCESS);\n" +
                "            ajaxBean.setMessage(\"上传成功!\");\n" +
                "            ajaxBean.setData(file<Field>);\n" +
                "            return this.ajaxJson(ajaxBean);\n" +
                "        } catch (IllegalStateException e) {\n" +
                "            e.printStackTrace();\n" +
                "        } catch (IOException e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "        ajaxBean.setStatus(AjaxStatus.ERROR);\n" +
                "        ajaxBean.setMessage(\"上传失败!\");\n" +
                "        return this.ajaxJson(ajaxBean);\n" +
                "    }";
        uploadCode = uploadCode.replaceAll("<Object>",StringUtil.getUpperStart(bean.getTableName()));
        uploadCode = uploadCode.replaceAll("<object>",StringUtil.getLowerStart(bean.getTableName()));
        String controllerCode="";
        for(int i=0;i<list.size();i++)
        {
            TableDescBO bo = list.get(i);
            String memoStr = bo.getMemo();
            String memo[] = memoStr.split("-");
            if(BeanEnum.FILE.getValue().equalsIgnoreCase(memo[0]))
            {
                controllerCode = controllerCode + uploadCode.replaceAll("<Field>",StringUtil.getUpperStart(bo.getField()));
                controllerCode = controllerCode.replaceAll("<field>",StringUtil.getLowerStart(bo.getField()));
                controllerCode = controllerCode +"\n";

            }else if(BeanEnum.IMAGE.getValue().equalsIgnoreCase(memo[0]))
            {
                controllerCode = controllerCode + uploadCode.replaceAll("<Field>",StringUtil.getUpperStart(bo.getField()));
                controllerCode = controllerCode.replaceAll("<field>",StringUtil.getLowerStart(bo.getField()));
                controllerCode = controllerCode +"\n";
            }
        }
        javaSource = javaSource.replaceAll("<UploadCodeHere>",controllerCode);
        return javaSource;
    }
    private String generateServiceClassText(TableBean bean) {
        String entityName = StringUtil.getUpperStart(bean.getTableName());
        String path = HtmlFactory.class.getResource(ROOT).getPath();
        String templateSource = FileUtil.readTxtFile(path+Constant.SERVICE_IMPL_TEMPALTE);
        String javaSource = "";
        javaSource = javaSource + "package "+servicesPackage+";\n";
        javaSource = javaSource + "import "+beansPackage+"."+entityName+";\n";
        javaSource = javaSource + "import "+beansPackage+"."+entityName+"Example;\n";
        javaSource = javaSource + "import "+mappersPackage+"."+entityName+"Mapper;\n";
        javaSource = javaSource + "import com.xz.web.utils.date.DateUtil;\n";
        javaSource = javaSource + "import "+basePackage+".BaseServiceImpl;\n";
        javaSource = javaSource + "import "+basePackage+".PageInfo;\n";
        javaSource = javaSource + "import com.xz.framework.utils.PageHelper;\n";
        javaSource = javaSource + "import org.springframework.beans.factory.annotation.Autowired;\n";
        javaSource = javaSource + "import org.springframework.stereotype.Service;\n";
        javaSource = javaSource + "import org.springframework.transaction.annotation.Transactional;\n";
        javaSource = javaSource + "import java.util.List;\n\n";
        javaSource = javaSource + templateSource.replaceAll("<Object>",entityName);
        String searchCondition="";
        searchCondition = searchCondition +"if (searchCondition != null) {\n";
        searchCondition = searchCondition +entityName+"Example.Criteria criteria = example.createCriteria();\n";
        List<TableDescBO>  list = bean.getTableDesc();
        for(int i=0;i<list.size();i++)
        {
            searchCondition = searchCondition +"if (searchCondition.get"+StringUtil.getUpperStart(list.get(i).getField())+"() != null) \n";
            searchCondition = searchCondition +"criteria.and"+StringUtil.getUpperStart(list.get(i).getField())+"EqualTo(searchCondition.get"+StringUtil.getUpperStart(list.get(i).getField())+"());\n";
        }
        searchCondition = searchCondition +"}\n";
        javaSource = javaSource.replaceAll("<UploadCodeHere>",searchCondition);
        return javaSource;
    }

    public void generateService(List<TableBean> beanlist, String javaSourcePath) {
        this.beanlist=beanlist;
        this.javaSourcePath=javaSourcePath;
        this.servicesPackage=servicesPackage;
        String servicesPackageTmp = servicesPackage.replaceAll("\\.","\\/");
        String controllersPackageTmp = controllersPackage.replaceAll("\\.","\\/");
        servicesPath = javaSourcePath + "/" +servicesPackageTmp+"/";
        controllerPath = javaSourcePath + "/" +controllersPackageTmp+"/";
        generateServiceInterface();
        generateServiceControllerClass();
    }

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
