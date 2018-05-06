package com.xz.web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xz.web.mapper.entity.Admin;
import com.xz.web.service.AdminService;
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

@org.springframework.stereotype.Controller("adminAdminController")
@RequestMapping("admin")
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    

    @RequestMapping("json/addAdmin")
    public
    @ResponseBody
    String addAdmin(Admin admin) {
        AjaxBean<Admin> ajaxBean = new AjaxBean<Admin>();
        Admin entity = new Admin();
        BeanUtil.copyProperties(admin, entity, Constant.NotNul);
        entity.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        int flag = adminService.add(entity);
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

    @RequestMapping("json/delAdminById")
    public
    @ResponseBody
    String delAdminById(Integer id) {
        AjaxBean<Integer> ajaxBean = new AjaxBean<Integer>();
        int flag = adminService.removeById(id);
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

    @RequestMapping("json/updateAdminById")
    public
    @ResponseBody
    String updateAdminById(Admin admin) {
        AjaxBean<String> ajaxBean = new AjaxBean<String>();
        Integer id = admin.getId();
        if(id!=null) {
            Admin entity = adminService.getById(id);
            if(null==entity) {
                ajaxBean.setStatus(AjaxStatus.ERROR);
                ajaxBean.setMessage("记录不存在!");
            }else
            {
                BeanUtil.copyProperties(admin,entity,Constant.NotNul);
                entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
                int flag = adminService.update(entity);
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

    @RequestMapping("json/getAdminById")
    public
    @ResponseBody
    String getAdminById(Integer id) {
        AjaxBean<Admin> ajaxBean = new AjaxBean<Admin>();
        Admin obj = adminService.getById(id);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(obj);
        return this.ajaxJson(ajaxBean);
    }

    @RequestMapping("json/findAdminsByPage")
    public
    @ResponseBody
    String findAdminsByPage(Admin searchCondition, PageInfo<Admin> pager) {
        AjaxBean<PageInfo<Admin>> ajaxBean = new AjaxBean<PageInfo<Admin>>();
        pager = adminService.findList(searchCondition, pager);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(pager);
        return this.ajaxJson(ajaxBean);
    }

}
