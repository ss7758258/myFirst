package com.xz.web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xz.web.mapper.entity.Example;
import com.xz.web.service.ExampleService;
import com.xz.framework.utils.bean.BeanUtil;
import com.xz.framework.utils.date.DateUtil;
import com.xz.framework.utils.string.StringUtil;
import com.xz.framework.common.base.AjaxBean;
import com.xz.framework.common.base.AjaxStatus;
import com.xz.framework.common.base.BaseController;
import com.xz.framework.common.base.PageInfo;
import com.xz.web.constant.Constants;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.xz.framework.utils.files.FileUtil;

@org.springframework.stereotype.Controller("exampleExampleController")
@RequestMapping("example")
public class ExampleController extends BaseController {

    @Autowired
    private ExampleService exampleService;

    @RequestMapping("json/urlUpload")
    public
    @ResponseBody
    String urlUpload(@RequestParam(name="file",required=false) MultipartFile url) {
        AjaxBean<String> ajaxBean = new AjaxBean<String>();
        if(url==null|| StringUtil.isEmpty(url.getOriginalFilename()))
        {
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage("文件为空!");
            return this.ajaxJson(ajaxBean);
        }
        try {
            String fileUrl = FileUtil.uploadFile("example", this.getRequest(), url);
            ajaxBean.setStatus(AjaxStatus.SUCCESS);
            ajaxBean.setMessage("上传成功!");
            ajaxBean.setData(fileUrl);
            return this.ajaxJson(ajaxBean);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ajaxBean.setStatus(AjaxStatus.ERROR);
        ajaxBean.setMessage("上传失败!");
        return this.ajaxJson(ajaxBean);
    }
@RequestMapping("json/fileUpload")
    public
    @ResponseBody
    String fileUpload(@RequestParam(name="file",required=false) MultipartFile file) {
        AjaxBean<String> ajaxBean = new AjaxBean<String>();
        if(file==null|| StringUtil.isEmpty(file.getOriginalFilename()))
        {
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage("文件为空!");
            return this.ajaxJson(ajaxBean);
        }
        try {
            String fileFile = FileUtil.uploadFile("example", this.getRequest(), file);
            ajaxBean.setStatus(AjaxStatus.SUCCESS);
            ajaxBean.setMessage("上传成功!");
            ajaxBean.setData(fileFile);
            return this.ajaxJson(ajaxBean);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ajaxBean.setStatus(AjaxStatus.ERROR);
        ajaxBean.setMessage("上传失败!");
        return this.ajaxJson(ajaxBean);
    }


    @RequestMapping("json/addExample")
    public
    @ResponseBody
    String addExample(Example example) {
        AjaxBean<Example> ajaxBean = new AjaxBean<Example>();
        Example entity = new Example();
        BeanUtil.copyProperties(example, entity, Constant.NotNul);
        entity.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        int flag = exampleService.add(entity);
        if(flag>0) {
            ajaxBean.setStatus(AjaxStatus.SUCCESS);
            ajaxBean.setMessage("增加成功!");
        }else
        {
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage("增加失败!");
        }
        return this.ajaxJson(ajaxBean);
    }

    @RequestMapping("json/delExampleById")
    public
    @ResponseBody
    String delExampleById(Integer id) {
        AjaxBean<Integer> ajaxBean = new AjaxBean<Integer>();
        int flag = exampleService.removeById(id);
        if(flag>0) {
            ajaxBean.setStatus(AjaxStatus.SUCCESS);
            ajaxBean.setMessage("删除成功!");
            ajaxBean.setData(flag);
        }else
        {
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage("删除失败!");
            ajaxBean.setData(flag);
        }
        return this.ajaxJson(ajaxBean);
    }

    @RequestMapping("json/updateExampleById")
    public
    @ResponseBody
    String updateExampleById(Example example) {
        AjaxBean<String> ajaxBean = new AjaxBean<String>();
        Integer id = example.getId();
        if(id!=null) {
            Example entity = exampleService.getById(id);
            if(null==entity) {
                ajaxBean.setStatus(AjaxStatus.ERROR);
                ajaxBean.setMessage("记录不存在!");
            }else
            {
                BeanUtil.copyProperties(example,entity,Constant.NotNul);
                entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
                int flag = exampleService.update(entity);
                if(flag>0) {
                    ajaxBean.setStatus(AjaxStatus.SUCCESS);
                    ajaxBean.setMessage("更新成功!");
                }else
                {
                    ajaxBean.setStatus(AjaxStatus.ERROR);
                    ajaxBean.setMessage("更新失败!");
                }
            }
        }else
        {
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage("记录不存在!");
        }
        return this.ajaxJson(ajaxBean);
    }

    @RequestMapping("json/getExampleById")
    public
    @ResponseBody
    String getExampleById(Integer id) {
        AjaxBean<Example> ajaxBean = new AjaxBean<Example>();
        Example obj = exampleService.getById(id);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(obj);
        return this.ajaxJson(ajaxBean);
    }

    @RequestMapping("json/findExamplesByPage")
    public
    @ResponseBody
    String findExamplesByPage(Example searchCondition, PageInfo<Example> pager) {
        AjaxBean<PageInfo<Example>> ajaxBean = new AjaxBean<PageInfo<Example>>();
        pager = exampleService.findList(searchCondition, pager);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(pager);
        return this.ajaxJson(ajaxBean);
    }

}
