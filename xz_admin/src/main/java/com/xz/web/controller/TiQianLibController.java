package com.xz.web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xz.web.entity.TiQianLib;
import com.xz.web.service.TiQianLibService;
import com.xz.framework.utils.bean.BeanUtil;
import com.xz.framework.utils.date.DateUtil;
import com.xz.framework.utils.string.StringUtil;
import com.xz.framework.common.base.AjaxBean;
import com.xz.framework.common.base.AjaxStatus;
import com.xz.framework.common.base.BaseController;
import com.xz.framework.common.base.PageInfo;
import com.xz.web.constant.Constant;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.xz.framework.utils.files.FileUtil;

@org.springframework.stereotype.Controller("tiQianLibTiQianLibController")
@RequestMapping("tiQianLib")
public class TiQianLibController extends BaseController {

    @Autowired
    private TiQianLibService tiQianLibService;

    @RequestMapping("json/picUpload")
    public
    @ResponseBody
    String picUpload(@RequestParam(name="file",required=false) MultipartFile pic) {
        AjaxBean<String> ajaxBean = new AjaxBean<String>();
        if(pic==null|| StringUtil.isEmpty(pic.getOriginalFilename()))
        {
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage("文件为空!");
            return this.ajaxJson(ajaxBean);
        }
        try {
            String filePic = FileUtil.uploadFile("tiQianLib", this.getRequest(), pic);
            ajaxBean.setStatus(AjaxStatus.SUCCESS);
            ajaxBean.setMessage("上传成功!");
            ajaxBean.setData(filePic);
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


    @RequestMapping("json/addTiQianLib")
    public
    @ResponseBody
    String addTiQianLib(TiQianLib tiQianLib) {
        AjaxBean<TiQianLib> ajaxBean = new AjaxBean<TiQianLib>();
        TiQianLib entity = new TiQianLib();
        BeanUtil.copyProperties(tiQianLib, entity, Constant.NotNul);
        entity.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        int flag = tiQianLibService.add(entity);
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

    @RequestMapping("json/delTiQianLibById")
    public
    @ResponseBody
    String delTiQianLibById(Long id) {
        AjaxBean<Integer> ajaxBean = new AjaxBean<Integer>();
        int flag = tiQianLibService.removeById(id);
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

    @RequestMapping("json/updateTiQianLibById")
    public
    @ResponseBody
    String updateTiQianLibById(TiQianLib tiQianLib) {
        AjaxBean<String> ajaxBean = new AjaxBean<String>();
        Long id = tiQianLib.getId();
        if(id!=null) {
            TiQianLib entity = tiQianLibService.getById(id);
            if(null==entity) {
                ajaxBean.setStatus(AjaxStatus.ERROR);
                ajaxBean.setMessage("记录不存在!");
            }else
            {
                BeanUtil.copyProperties(tiQianLib,entity,Constant.NotNul);
                entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
                int flag = tiQianLibService.update(entity);
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

    @RequestMapping("json/getTiQianLibById")
    public
    @ResponseBody
    String getTiQianLibById(Long id) {
        AjaxBean<TiQianLib> ajaxBean = new AjaxBean<TiQianLib>();
        TiQianLib obj = tiQianLibService.getById(id);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(obj);
        return this.ajaxJson(ajaxBean);
    }

    @RequestMapping("json/findTiQianLibsByPage")
    public
    @ResponseBody
    String findTiQianLibsByPage(TiQianLib searchCondition, PageInfo<TiQianLib> pager) {
        AjaxBean<PageInfo<TiQianLib>> ajaxBean = new AjaxBean<PageInfo<TiQianLib>>();
        pager = tiQianLibService.findList(searchCondition, pager);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(pager);
        return this.ajaxJson(ajaxBean);
    }

}
