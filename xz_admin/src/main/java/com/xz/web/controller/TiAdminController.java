package com.xz.web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xz.web.entity.TiAdmin;
import com.xz.web.service.TiAdminService;
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

@org.springframework.stereotype.Controller("tiAdminTiAdminController")
@RequestMapping("tiAdmin")
public class TiAdminController extends BaseController {

    @Autowired
    private TiAdminService tiAdminService;

    

    @RequestMapping("json/addTiAdmin")
    public
    @ResponseBody
    String addTiAdmin(TiAdmin tiAdmin) {
        AjaxBean<TiAdmin> ajaxBean = new AjaxBean<TiAdmin>();
        TiAdmin entity = new TiAdmin();
        BeanUtil.copyProperties(tiAdmin, entity, Constant.NotNul);
        entity.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        int flag = tiAdminService.add(entity);
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

    @RequestMapping("json/delTiAdminById")
    public
    @ResponseBody
    String delTiAdminById(Long id) {
        AjaxBean<Integer> ajaxBean = new AjaxBean<Integer>();
        int flag = tiAdminService.removeById(id);
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

    @RequestMapping("json/updateTiAdminById")
    public
    @ResponseBody
    String updateTiAdminById(TiAdmin tiAdmin) {
        AjaxBean<String> ajaxBean = new AjaxBean<String>();
        Long id = tiAdmin.getId();
        if(id!=null) {
            TiAdmin entity = tiAdminService.getById(id);
            if(null==entity) {
                ajaxBean.setStatus(AjaxStatus.ERROR);
                ajaxBean.setMessage("记录不存在!");
            }else
            {
                BeanUtil.copyProperties(tiAdmin,entity,Constant.NotNul);
                entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
                int flag = tiAdminService.update(entity);
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

    @RequestMapping("json/getTiAdminById")
    public
    @ResponseBody
    String getTiAdminById(Long id) {
        AjaxBean<TiAdmin> ajaxBean = new AjaxBean<TiAdmin>();
        TiAdmin obj = tiAdminService.getById(id);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(obj);
        return this.ajaxJson(ajaxBean);
    }

    @RequestMapping("json/findTiAdminsByPage")
    public
    @ResponseBody
    String findTiAdminsByPage(TiAdmin searchCondition, PageInfo<TiAdmin> pager) {
        AjaxBean<PageInfo<TiAdmin>> ajaxBean = new AjaxBean<PageInfo<TiAdmin>>();
        pager = tiAdminService.findList(searchCondition, pager);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(pager);
        return this.ajaxJson(ajaxBean);
    }

}
