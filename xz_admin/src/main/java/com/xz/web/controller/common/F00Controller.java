package com.xz.web.controller.common;

import com.xz.framework.common.base.AjaxBean;
import com.xz.framework.common.base.AjaxStatus;
import com.xz.framework.common.base.BaseController;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.utils.date.DateUtil;
import com.xz.framework.utils.json.JsonUtil;
import com.xz.framework.utils.string.StringUtil;
import com.xz.web.constant.Constant;
import com.xz.web.entity.TiAdmin;
import com.xz.web.entity.TiQianLib;
import com.xz.web.entity.TiQianList;
import com.xz.web.service.TiAdminService;
import com.xz.web.service.TiQianLibService;
import com.xz.web.service.TiQianListService;
import com.xz.web.vo.AdminLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/f00")
@Controller
public class F00Controller extends BaseController {

    @Autowired
    TiAdminService adminService;
    @Autowired
    private TiQianLibService tiQianLibService;
    @Autowired
    private TiQianListService tiQianListService;

    @RequestMapping("login")
    @ResponseBody
    public String login(AdminLoginVo obj) {
        AjaxBean<PageInfo<TiAdmin>> ajaxBean = new AjaxBean<PageInfo<TiAdmin>>();
        if(null==obj|| StringUtil.isEmpty(obj.getUsername()))
        {
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage("用户名称为空!");
            return JsonUtil.serialize(ajaxBean);
        }
        if(null==obj|| StringUtil.isEmpty(obj.getPassword()))
        {
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage("用户密码为空!");
            return JsonUtil.serialize(ajaxBean);
        }
        try {
            TiAdmin searchCondition = new TiAdmin();
            searchCondition.setUsername(obj.getUsername());
            searchCondition.setPassword(obj.getPassword());
            PageInfo<TiAdmin> pager = new PageInfo<TiAdmin>();
            pager = adminService.findList(searchCondition, pager);
            List<TiAdmin> list = pager.getList();
            if(list.size()>0)
            {
                ajaxBean.setStatus(AjaxStatus.SUCCESS);
                ajaxBean.setMessage("登录成功!");
                TiAdmin sessionUser = list.get(0);
                this.getRequest().getSession().setAttribute(Constant.ADMIN_SESSION,sessionUser);
                return this.ajaxJson(ajaxBean);
            }else
            {
                ajaxBean.setStatus(AjaxStatus.ERROR);
                ajaxBean.setMessage("用户不存在!");
                return this.ajaxJson(ajaxBean);
            }
        } catch (Exception e) {
            e.printStackTrace();;
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage(e.getMessage());
        }
        return this.ajaxJson(ajaxBean);
    }

    @RequestMapping("logout")
    @ResponseBody
    public String logout() {
        AjaxBean<Void> ajaxBean = new AjaxBean<Void>();
        try {
            this.getRequest().getSession().setAttribute(Constant.ADMIN_SESSION,null);
            ajaxBean.setStatus(AjaxStatus.SUCCESS);
            ajaxBean.setMessage("登出成功!");
            return this.ajaxJson(ajaxBean);
        } catch (Exception e) {
            e.printStackTrace();;
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage(e.getMessage());
        }
        return this.ajaxJson(ajaxBean);
    }

    @RequestMapping("qianDisable")
    @ResponseBody
    public String qianDisable(Long id) {
        AjaxBean<TiQianLib> ajaxBean = new AjaxBean<TiQianLib>();
        try {
            TiQianLib obj = tiQianLibService.getById(id);
            obj.setStatus(0);
            obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
            tiQianLibService.update(obj);
            ajaxBean.setStatus(AjaxStatus.SUCCESS);
            ajaxBean.setMessage("暂停成功!");
            return this.ajaxJson(ajaxBean);
        } catch (Exception e) {
            e.printStackTrace();;
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage(e.getMessage());
        }
        return this.ajaxJson(ajaxBean);
    }

    @RequestMapping("qianListByLibId")
    @ResponseBody
    public String qianListByLibId(Long id,Integer pageNum,Integer pageSize) {
        AjaxBean<PageInfo<TiQianList>> ajaxBean = new AjaxBean<PageInfo<TiQianList>>();
        try {
            if(null==pageNum)pageNum=1;;
            if(null==pageSize)pageSize=10;;
            TiQianList searchCondition = new TiQianList();
            searchCondition.setQianLibId(id);
            PageInfo<TiQianList> pager = new PageInfo<TiQianList>();
            pager.setPageNum(pageNum);
            pager.setPageSize(pageSize);
            pager = tiQianListService.findList(searchCondition, pager);
            ajaxBean.setStatus(AjaxStatus.SUCCESS);
            ajaxBean.setMessage("查询成功!");
            ajaxBean.setData(pager);
            return this.ajaxJson(ajaxBean);
        } catch (Exception e) {
            e.printStackTrace();;
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage(e.getMessage());
        }
        return this.ajaxJson(ajaxBean);
    }
}


