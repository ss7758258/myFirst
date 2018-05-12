package com.xz.web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xz.web.entity.TcConstellation;
import com.xz.web.service.TcConstellationService;
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

@org.springframework.stereotype.Controller("tcConstellationTcConstellationController")
@RequestMapping("tcConstellation")
public class TcConstellationController extends BaseController {

    @Autowired
    private TcConstellationService tcConstellationService;

    @RequestMapping("json/pictureUrlUpload")
    public
    @ResponseBody
    String pictureUrlUpload(@RequestParam(name="file",required=false) MultipartFile pictureUrl) {
        AjaxBean<String> ajaxBean = new AjaxBean<String>();
        if(pictureUrl==null|| StringUtil.isEmpty(pictureUrl.getOriginalFilename()))
        {
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage("文件为空!");
            return this.ajaxJson(ajaxBean);
        }
        try {
            String filePictureUrl = FileUtil.uploadFile("tcConstellation", this.getRequest(), pictureUrl);
            ajaxBean.setStatus(AjaxStatus.SUCCESS);
            ajaxBean.setMessage("上传成功!");
            ajaxBean.setData(filePictureUrl);
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


    @RequestMapping("json/addTcConstellation")
    public
    @ResponseBody
    String addTcConstellation(TcConstellation tcConstellation) {
        AjaxBean<TcConstellation> ajaxBean = new AjaxBean<TcConstellation>();
        TcConstellation entity = new TcConstellation();
        BeanUtil.copyProperties(tcConstellation, entity, Constant.NotNul);
        entity.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        int flag = tcConstellationService.add(entity);
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

    @RequestMapping("json/delTcConstellationById")
    public
    @ResponseBody
    String delTcConstellationById(Long id) {
        AjaxBean<Integer> ajaxBean = new AjaxBean<Integer>();
        int flag = tcConstellationService.removeById(id);
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

    @RequestMapping("json/updateTcConstellationById")
    public
    @ResponseBody
    String updateTcConstellationById(TcConstellation tcConstellation) {
        AjaxBean<String> ajaxBean = new AjaxBean<String>();
        Long id = tcConstellation.getId();
        if(id!=null) {
            TcConstellation entity = tcConstellationService.getById(id);
            if(null==entity) {
                ajaxBean.setStatus(AjaxStatus.ERROR);
                ajaxBean.setMessage("记录不存在!");
            }else
            {
                BeanUtil.copyProperties(tcConstellation,entity,Constant.NotNul);
                entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
                int flag = tcConstellationService.update(entity);
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

    @RequestMapping("json/getTcConstellationById")
    public
    @ResponseBody
    String getTcConstellationById(Long id) {
        AjaxBean<TcConstellation> ajaxBean = new AjaxBean<TcConstellation>();
        TcConstellation obj = tcConstellationService.getById(id);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(obj);
        return this.ajaxJson(ajaxBean);
    }

    @RequestMapping("json/findTcConstellationsByPage")
    public
    @ResponseBody
    String findTcConstellationsByPage(TcConstellation searchCondition, PageInfo<TcConstellation> pager) {
        AjaxBean<PageInfo<TcConstellation>> ajaxBean = new AjaxBean<PageInfo<TcConstellation>>();
        pager = tcConstellationService.findList(searchCondition, pager);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(pager);
        return this.ajaxJson(ajaxBean);
    }

}
