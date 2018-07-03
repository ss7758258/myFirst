package com.xz.web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xz.web.entity.WeixinUser;
import com.xz.web.service.WeixinUserService;
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

@org.springframework.stereotype.Controller("weixinUserWeixinUserController")
@RequestMapping("weixinUser")
public class WeixinUserController extends BaseController {

    @Autowired
    private WeixinUserService weixinUserService;

    

    @RequestMapping("json/addWeixinUser")
    public
    @ResponseBody
    String addWeixinUser(WeixinUser weixinUser) {
        AjaxBean<WeixinUser> ajaxBean = new AjaxBean<WeixinUser>();
        WeixinUser entity = new WeixinUser();
        BeanUtil.copyProperties(weixinUser, entity, Constant.NotNul);
        entity.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        int flag = weixinUserService.add(entity);
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

    @RequestMapping("json/delWeixinUserById")
    public
    @ResponseBody
    String delWeixinUserById(Long id) {
        AjaxBean<Integer> ajaxBean = new AjaxBean<Integer>();
        int flag = weixinUserService.removeById(id);
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

    @RequestMapping("json/updateWeixinUserById")
    public
    @ResponseBody
    String updateWeixinUserById(WeixinUser weixinUser) {
        AjaxBean<String> ajaxBean = new AjaxBean<String>();
        Long id = weixinUser.getId();
        if(id!=null) {
            WeixinUser entity = weixinUserService.getById(id);
            if(null==entity) {
                ajaxBean.setStatus(AjaxStatus.ERROR);
                ajaxBean.setMessage("记录不存在!");
            }else
            {
                BeanUtil.copyProperties(weixinUser,entity,Constant.NotNul);
                entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
                int flag = weixinUserService.update(entity);
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

    @RequestMapping("json/getWeixinUserById")
    public
    @ResponseBody
    String getWeixinUserById(Long id) {
        AjaxBean<WeixinUser> ajaxBean = new AjaxBean<WeixinUser>();
        WeixinUser obj = weixinUserService.getById(id);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(obj);
        return this.ajaxJson(ajaxBean);
    }

    @RequestMapping("json/findWeixinUsersByPage")
    public
    @ResponseBody
    String findWeixinUsersByPage(WeixinUser searchCondition, PageInfo<WeixinUser> pager) {
        AjaxBean<PageInfo<WeixinUser>> ajaxBean = new AjaxBean<PageInfo<WeixinUser>>();
        pager = weixinUserService.findList(searchCondition, pager);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(pager);
        return this.ajaxJson(ajaxBean);
    }

}
