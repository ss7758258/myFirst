package im.admin.generator.factory;


import im.admin.generator.bean.HtmlBean;
import im.admin.generator.bean.TableBean;
import im.admin.generator.constant.Constant;
import im.admin.generator.db.tools.TableDescBO;
import im.admin.generator.enums.BeanEnum;
import im.admin.generator.utils.files.FileUtil;
import im.admin.generator.utils.string.StringUtil;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HtmlFactory {
    private static Logger logger = Logger.getLogger(HtmlFactory.class);
    private static final String ROOT = Constant.ROOT;
    private static final String HTML_PATH = Constant.HTML_PATH;
    private static final String TEMPLATE_PATH = Constant.TEMPLATE_PATH;
    private static final String HTML_END = Constant.HTML_END;
    private static final String HTML_START = Constant.HTML_START;
    private static final String BODY = Constant.BODY;
    private static final String HEADER = Constant.HEADER;
    private static final String ADD = Constant.ADD;
    private static final String UPDATE = Constant.UPDATE;
    private static final String LIST = Constant.LIST;
    private static final String VIEW = Constant.VIEW;
    private String templatePath = "";
    private String filePath = "";
    private String htmlPath = "";
    private HtmlBean htmlBean = new HtmlBean();

    public HtmlBean getHtmlBean() {
        return htmlBean;
    }

    public void setHtmlBean(HtmlBean htmlBean) {
        this.htmlBean = htmlBean;
    }

    public HtmlFactory() {
        String path = HtmlFactory.class.getResource(ROOT).getPath();
        this.templatePath = path + TEMPLATE_PATH;
        this.htmlPath = path + HTML_PATH;
    }


    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public void generateHtml(HtmlBean htmlBean) {
        String filename = StringUtil.getLowerStart(htmlBean.getTableName());
        //TODO:List
        filePath = htmlPath + filename+LIST+".html";
        String html="";
        FileUtil.deleteTxtFile(filePath);
        //HTML_START
        html = FileUtil.readTxtFile(templatePath + HTML_START);
        FileUtil.writeTxtFile(filePath, html);
        //HEADER
        generateHeader(LIST);
        //BODY
        generateBody(LIST);
        //HTML_END
        html = FileUtil.readTxtFile(templatePath + HTML_END);
        FileUtil.writeTxtFile(filePath, html);
        //TODO:View
        filePath = htmlPath + filename+VIEW+".html";
        html="";
        FileUtil.deleteTxtFile(filePath);
        //HTML_START
        html = FileUtil.readTxtFile(templatePath + HTML_START);
        FileUtil.writeTxtFile(filePath, html);
        //HEADER
        generateHeader(VIEW);
        //BODY
        generateBody(VIEW);
        //HTML_END
        html = FileUtil.readTxtFile(templatePath + HTML_END);
        FileUtil.writeTxtFile(filePath, html);
        //TODO:Add
        filePath = htmlPath + filename+ADD+".html";
        html="";
        FileUtil.deleteTxtFile(filePath);
        //HTML_START
        html = FileUtil.readTxtFile(templatePath + HTML_START);
        FileUtil.writeTxtFile(filePath, html);
        //HEADER
        generateHeader(ADD);
        //BODY
        generateBody(ADD);
        //HTML_END
        html = FileUtil.readTxtFile(templatePath + HTML_END);
        FileUtil.writeTxtFile(filePath, html);
        //TODO:Update
        filePath = htmlPath + filename+UPDATE+".html";
        html="";
        FileUtil.deleteTxtFile(filePath);
        //HTML_START
        html = FileUtil.readTxtFile(templatePath + HTML_START);
        FileUtil.writeTxtFile(filePath, html);
        //HEADER
        generateHeader(UPDATE);
        //BODY
        generateBody(UPDATE);
        //HTML_END
        html = FileUtil.readTxtFile(templatePath + HTML_END);
        FileUtil.writeTxtFile(filePath, html);
    }
    private void generateHeader(String type) {
        String html = FileUtil.readTxtFile(templatePath +type+"/"+ HEADER);
        processHeader(html,type);
    }
    private void processHeader(String html,String type) {
        List<String> tags = getTags(html);
        html = injectTags(html,tags,type);
        FileUtil.writeTxtFile(filePath, html);
    }
    private void generateBody(String type) {
        if(type.equals(LIST)) {
            String html = FileUtil.readTxtFile(templatePath + type + "/" + BODY);
            processBody(html, type);
        }
        else if(type.equals(ADD)) {
            String html = FileUtil.readTxtFile(templatePath + type + "/" + BODY);
            processBody(html, type);
        }
        else if(type.equals(UPDATE)) {
            String html = FileUtil.readTxtFile(templatePath + type + "/" + BODY);
            processBody(html, type);
        }
        else if(type.equals(VIEW)) {
            String html = FileUtil.readTxtFile(templatePath + type + "/" + BODY);
            processBody(html, type);
        }
    }
    private void processBody(String html,String type) {
        List<String> tags1 = getTags(html);
        html = injectTags(html,tags1,type);
        List<String> tags2 = getTags(html);
        html = injectTags(html,tags2,type);
        FileUtil.writeTxtFile(filePath, html);
    }

    private String injectTags(String html, List<String> tags,String type) {
        for(int i=0;i<tags.size();i++)
        {
            String tag = tags.get(i);
            //引入的是页面
            if(tag.indexOf(".")>0) {
                if(StringUtil.isNotEmpty(type))
                    type =type+"/";
                String includeHtml = FileUtil.readTxtFile(templatePath+type + tag);
                html = html.replaceAll(Constant.START_TAGS + tag + Constant.END_TAGS, includeHtml);
            }else
            {
                String tagGetMethod = StringUtil.getGetMethod(tag);
                try {
                    Method method = this.getHtmlBean().getClass().getMethod(tagGetMethod, new Class[]{});
                    Object object = method.invoke(this.getHtmlBean(), new Object[0]);
                    html = html.replaceAll(Constant.START_TAGS + tag + Constant.END_TAGS, object.toString());
                } catch (NoSuchMethodException e) {
                    logger.error("没有找到该变量：" + tag);
                    //e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return html;
    }

    private List<String> getTags(String html) {
        List<String> tags= new ArrayList<String>() ;
        while(true)
        {
            int beginIndex = html.indexOf(Constant.START_TAGS);
            int endIndex =html.indexOf(Constant.END_TAGS);
            if(beginIndex>=0&&endIndex>0&&beginIndex<endIndex)
            {
                String tag = html.substring(beginIndex+Constant.START_TAGS.length(),endIndex);
                tags.add(tag);
                html = html.substring(endIndex+Constant.END_TAGS.length(),html.length());
            }else if(beginIndex<0&&endIndex<0) {
                break;
            }else
            {
                html = html.substring(endIndex+Constant.END_TAGS.length(),html.length());
                continue;
            }
        }
        return tags;
    }

    public void generateJS(String tag,TableBean tableBean) {
        String jsInputPath = templatePath + tag + "/template.js";
        String jsOutputPath = htmlPath + "js/web/";
        String jsOutputFile = jsOutputPath + tableBean.getTableName()+".js";
        String outData = FileUtil.readTxtFile(jsInputPath);
        String header="";
        String content="";
        String preContent="";
        String jsAddContent="";
        String jsGetContent="";
        String jsUpdateContent="";

        String jsAddData="var data = {";
        String jsUpdateData="var data = {";
        for(int i=0;i<tableBean.getTableDesc().size();i++)
        {
            TableDescBO bo = tableBean.getTableDesc().get(i);
            String memoStr = bo.getMemo();
            String memo[] = memoStr.split("-");
            if (bo.getField().equalsIgnoreCase("create_timestamp")||bo.getField().equalsIgnoreCase("update_timestamp")) {
                continue;
            }
            if (bo.getField().equalsIgnoreCase("ID")) {
                header = header + "<th>#ID</th>";
                content = content + "<td>'+obj." + StringUtil.getLowerStart(bo.getField()) + "+'</td>";
                jsGetContent = jsGetContent + "$(\"#"+StringUtil.getLowerStart(bo.getField())+"\").val(result." + StringUtil.getLowerStart(bo.getField()) + ");";
                jsUpdateContent = jsUpdateContent +"var "+StringUtil.getLowerStart(bo.getField())+"Field  = $(\"#"+StringUtil.getLowerStart(bo.getField())+"\").val();";
                jsUpdateData = jsUpdateData + StringUtil.getLowerStart(bo.getField())+": "+StringUtil.getLowerStart(bo.getField())+"Field,";
            }
            else if(BeanEnum.IGNORE.getValue().equalsIgnoreCase(memo[0])||memo.length<=1)
            {
                jsAddData = jsAddData + StringUtil.getLowerStart(bo.getField())+": "+StringUtil.getLowerStart(bo.getField())+"Field,";
                jsUpdateData = jsUpdateData + StringUtil.getLowerStart(bo.getField())+": "+StringUtil.getLowerStart(bo.getField())+"Field,";
                jsAddContent = jsAddContent +"var "+StringUtil.getLowerStart(bo.getField())+"Field  = $(\"#"+StringUtil.getLowerStart(bo.getField())+"\").val();";
                jsUpdateContent = jsUpdateContent +"var "+StringUtil.getLowerStart(bo.getField())+"Field  = $(\"#"+StringUtil.getLowerStart(bo.getField())+"\").val();";
                jsGetContent = jsGetContent + "$(\"#"+StringUtil.getLowerStart(bo.getField())+"\").val(result." + StringUtil.getLowerStart(bo.getField()) + ");";
                continue;
            }
            else if(BeanEnum.FILE.getValue().equalsIgnoreCase(memo[0]))
            {
                jsAddData = jsAddData + StringUtil.getLowerStart(bo.getField())+": "+StringUtil.getLowerStart(bo.getField())+"Field,";
                jsUpdateData = jsUpdateData + StringUtil.getLowerStart(bo.getField())+": "+StringUtil.getLowerStart(bo.getField())+"Field,";
                jsAddContent = jsAddContent +"var "+StringUtil.getLowerStart(bo.getField())+"Field = $('#"+StringUtil.getLowerStart(bo.getField())+"Span').html();";
                jsUpdateContent = jsUpdateContent +"var "+StringUtil.getLowerStart(bo.getField())+"Field = $('#"+StringUtil.getLowerStart(bo.getField())+"Span').html();";
                jsGetContent = jsGetContent + "$(\"#"+StringUtil.getLowerStart(bo.getField())+"Span\").html(result." + StringUtil.getLowerStart(bo.getField()) + ");";
                continue;
            }
            else if(BeanEnum.IMAGE.getValue().equalsIgnoreCase(memo[0]))
            {
                header = header + "<th>" + memo[1] + "</th>";
                content = content + "<td><img src='+obj." + StringUtil.getLowerStart(bo.getField()) + "+'></img></td>";
                jsAddData = jsAddData + StringUtil.getLowerStart(bo.getField())+": "+StringUtil.getLowerStart(bo.getField())+"Field,";
                jsUpdateData = jsUpdateData + StringUtil.getLowerStart(bo.getField())+": "+StringUtil.getLowerStart(bo.getField())+"Field,";
                jsAddContent = jsAddContent +"var "+StringUtil.getLowerStart(bo.getField())+"Field = $(\"#"+StringUtil.getLowerStart(bo.getField())+"Span\").html();";
                jsUpdateContent = jsUpdateContent +"var "+StringUtil.getLowerStart(bo.getField())+"Field = $(\"#"+StringUtil.getLowerStart(bo.getField())+"Span\").html();";
                jsGetContent = jsGetContent + "$(\"#"+StringUtil.getLowerStart(bo.getField())+"Span\").html(result." + StringUtil.getLowerStart(bo.getField()) + ");";
            }
            else if(memo.length==2) {
                header = header + "<th>" + memo[1] + "</th>";
                content = content + "<td>'+obj." + StringUtil.getLowerStart(bo.getField()) + "+'</td>";
                jsAddData = jsAddData + StringUtil.getLowerStart(bo.getField())+": "+StringUtil.getLowerStart(bo.getField())+"Field,";
                jsUpdateData = jsUpdateData + StringUtil.getLowerStart(bo.getField())+": "+StringUtil.getLowerStart(bo.getField())+"Field,";
                jsAddContent = jsAddContent +"var "+StringUtil.getLowerStart(bo.getField())+"Field  = $(\"#"+StringUtil.getLowerStart(bo.getField())+"\").val();";
                jsUpdateContent = jsUpdateContent +"var "+StringUtil.getLowerStart(bo.getField())+"Field  = $(\"#"+StringUtil.getLowerStart(bo.getField())+"\").val();";
                jsGetContent = jsGetContent + "$(\"#"+StringUtil.getLowerStart(bo.getField())+"\").val(result." + StringUtil.getLowerStart(bo.getField()) + ");";
            }
            //三列开始
            else if(BeanEnum.RADIO_BOOLEAN.getValue().equalsIgnoreCase(memo[0]))
            {
                String value = memo[2];
                String values[] = value.split(",");
                if(values.length<=1) continue;
                preContent = preContent +"var " + StringUtil.getLowerStart(bo.getField()) + ";";
                preContent = preContent + "if(obj." + StringUtil.getLowerStart(bo.getField()) + "==true)" + StringUtil.getLowerStart(bo.getField()) + " = '"+values[0]+"';";
                preContent = preContent + "if(obj." + StringUtil.getLowerStart(bo.getField()) + "==false)" + StringUtil.getLowerStart(bo.getField()) + " = '"+values[1]+"';";
                header = header + "<th>" + memo[1] + "</th>";
                content = content + "<td>'+" + StringUtil.getLowerStart(bo.getField()) + "+'</td>";
                jsAddData = jsAddData + StringUtil.getLowerStart(bo.getField())+": "+StringUtil.getLowerStart(bo.getField())+"Field,";
                jsUpdateData = jsUpdateData + StringUtil.getLowerStart(bo.getField())+": "+StringUtil.getLowerStart(bo.getField())+"Field,";
                jsAddContent = jsAddContent +"var "+StringUtil.getLowerStart(bo.getField())+"Field=$('input:radio[name=\""+StringUtil.getLowerStart(bo.getField())+"\"]:checked').val();";
                jsUpdateContent = jsUpdateContent +"var "+StringUtil.getLowerStart(bo.getField())+"Field=$('input:radio[name=\""+StringUtil.getLowerStart(bo.getField())+"\"]:checked').val();";
                jsGetContent = jsGetContent + "$(\"input[name='"+StringUtil.getLowerStart(bo.getField())+"']\").eq(!result."+StringUtil.getLowerStart(bo.getField())+").attr(\"checked\",\"checked\");";

            }else if(BeanEnum.SELECT_INT.getValue().equalsIgnoreCase(memo[0]))
            {
                String value = memo[2];
                String values[] = value.split(",");
                if(values.length<=1) continue;
                preContent = preContent +"var " + StringUtil.getLowerStart(bo.getField()) + ";";
                for(int k =0;k<values.length;k++)
                {
                    String v = values[k];
                    String vs[] = v.split("=");
                    preContent = preContent + "if(obj." + StringUtil.getLowerStart(bo.getField()) + "=="+vs[0]+")" + StringUtil.getLowerStart(bo.getField()) + " = '"+vs[1]+"';";
                }
                header = header + "<th>" + memo[1] + "</th>";
                content = content + "<td>'+" + StringUtil.getLowerStart(bo.getField()) + "+'</td>";
                jsAddData = jsAddData + StringUtil.getLowerStart(bo.getField())+": "+StringUtil.getLowerStart(bo.getField())+"Field,";
                jsUpdateData = jsUpdateData + StringUtil.getLowerStart(bo.getField())+": "+StringUtil.getLowerStart(bo.getField())+"Field,";
                jsAddContent = jsAddContent +"var "+StringUtil.getLowerStart(bo.getField())+"Field  = $(\"#"+StringUtil.getLowerStart(bo.getField())+"\").val();";
                jsUpdateContent = jsUpdateContent +"var "+StringUtil.getLowerStart(bo.getField())+"Field  = $(\"#"+StringUtil.getLowerStart(bo.getField())+"\").val();";
                jsGetContent = jsGetContent + "$(\"#"+StringUtil.getLowerStart(bo.getField())+"\").val(result." + StringUtil.getLowerStart(bo.getField()) + ");";
            }else if(BeanEnum.SELECT_STRING.getValue().equalsIgnoreCase(memo[0]))
            {
                String value = memo[2];
                String values[] = value.split(",");
                if(values.length<=1) continue;
                preContent = preContent +"var " + StringUtil.getLowerStart(bo.getField()) + "='未知';";
                for(int k =0;k<values.length;k++)
                {
                    String v = values[k];
                    String vs[] = v.split("=");
                    preContent = preContent + "if(obj." + StringUtil.getLowerStart(bo.getField()) + "=='"+vs[0]+"')" + StringUtil.getLowerStart(bo.getField()) + " = '"+vs[1]+"';";
                }
                header = header + "<th>" + memo[1] + "</th>";
                content = content + "<td>'+" + StringUtil.getLowerStart(bo.getField()) + "+'</td>";
                jsAddData = jsAddData + StringUtil.getLowerStart(bo.getField())+": "+StringUtil.getLowerStart(bo.getField())+"Field,";
                jsUpdateData = jsUpdateData + StringUtil.getLowerStart(bo.getField())+": "+StringUtil.getLowerStart(bo.getField())+"Field,";
                jsAddContent = jsAddContent +"var "+StringUtil.getLowerStart(bo.getField())+"Field  = $(\"#"+StringUtil.getLowerStart(bo.getField())+"\").val();";
                jsUpdateContent = jsUpdateContent +"var "+StringUtil.getLowerStart(bo.getField())+"Field  = $(\"#"+StringUtil.getLowerStart(bo.getField())+"\").val();";
                jsGetContent = jsGetContent + "$(\"#"+StringUtil.getLowerStart(bo.getField())+"\").val(result." + StringUtil.getLowerStart(bo.getField()) + ");";
            }else {
                if (memo.length > 1) {
                    header = header + "<th>" + memo[1] + "</th>";
                    content = content + "<td>'+obj." + StringUtil.getLowerStart(bo.getField()) + "+'</td>";
                    jsAddData = jsAddData + StringUtil.getLowerStart(bo.getField())+": "+StringUtil.getLowerStart(bo.getField())+"Field,";
                    jsUpdateData = jsUpdateData + StringUtil.getLowerStart(bo.getField())+": "+StringUtil.getLowerStart(bo.getField())+"Field,";
                    jsAddContent = jsAddContent +"var "+StringUtil.getLowerStart(bo.getField())+"Field  = $(\"#"+StringUtil.getLowerStart(bo.getField())+"\").val();";
                    jsUpdateContent = jsUpdateContent +"var "+StringUtil.getLowerStart(bo.getField())+"Field  = $(\"#"+StringUtil.getLowerStart(bo.getField())+"\").val();";
                    jsGetContent = jsGetContent + "$(\"#"+StringUtil.getLowerStart(bo.getField())+"\").val(result." + StringUtil.getLowerStart(bo.getField()) + ");";
                }
            }
        }
        jsAddData = jsAddData +"testXXX:1};";
        jsUpdateData = jsUpdateData +"testXXX:1};";
        preContent = preContent +"\n";
        outData = outData.replaceAll("<#_S>header</#_E>",header);
        outData = outData.replaceAll("//<#_S>preContent</#_E>",preContent);
        jsAddContent = java.util.regex.Matcher.quoteReplacement(jsAddContent)+"\n\t"+jsAddData;
        outData = outData.replaceAll("//<#_S>jsAddContent</#_E>",jsAddContent);
        jsGetContent = java.util.regex.Matcher.quoteReplacement(jsGetContent);
        outData = outData.replaceAll("//<#_S>jsGetContent</#_E>",jsGetContent);
        jsUpdateContent = java.util.regex.Matcher.quoteReplacement(jsUpdateContent)+"\n\t"+jsUpdateData;
        outData = outData.replaceAll("//<#_S>jsUpdateContent</#_E>",jsUpdateContent);
        outData = outData.replaceAll("<#_S>content</#_E>",content);
        outData = outData.replaceAll("<#_S>key</#_E>",StringUtil.getLowerStart(tableBean.getTableName()));
        outData = outData.replaceAll("<#_S>Key</#_E>",StringUtil.getUpperStart(tableBean.getTableName()));
        FileUtil.deleteTxtFile(jsOutputFile);
        FileUtil.writeTxtFile(jsOutputFile, outData);
    }

    public void outputJS(HtmlBean htmlBean) {
        TableBean tableBean = htmlBean.getTableBean();
        generateJS(LIST, tableBean);
    }
}
