package com.xz.web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xz.web.entity.TiUserQianList;
import com.xz.web.service.TiUserQianListService;
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

@org.springframework.stereotype.Controller("tiUserQianListTiUserQianListController")
@RequestMapping("tiUserQianList")
public class TiUserQianListController extends BaseController {

    @Autowired
    private TiUserQianListService tiUserQianListService;

    

    @RequestMapping("json/addTiUserQianList")
    public
    @ResponseBody
    String addTiUserQianList(TiUserQianList tiUserQianList) {
        AjaxBean<TiUserQianList> ajaxBean = new AjaxBean<TiUserQianList>();
        TiUserQianList entity = new TiUserQianList();
        BeanUtil.copyProperties(tiUserQianList, entity, Constant.NotNul);
        entity.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        int flag = tiUserQianListService.add(entity);
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

    @RequestMapping("json/delTiUserQianListById")
    public
    @ResponseBody
    String delTiUserQianListById(Long id) {
        AjaxBean<Integer> ajaxBean = new AjaxBean<Integer>();
        int flag = tiUserQianListService.removeById(id);
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

    @RequestMapping("json/updateTiUserQianListById")
    public
    @ResponseBody
    String updateTiUserQianListById(TiUserQianList tiUserQianList) {
        AjaxBean<String> ajaxBean = new AjaxBean<String>();
        Long id = tiUserQianList.getId();
        if(id!=null) {
            TiUserQianList entity = tiUserQianListService.getById(id);
            if(null==entity) {
                ajaxBean.setStatus(AjaxStatus.ERROR);
                ajaxBean.setMessage("记录不存在!");
            }else
            {
                BeanUtil.copyProperties(tiUserQianList,entity,Constant.NotNul);
                entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
                int flag = tiUserQianListService.update(entity);
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

    @RequestMapping("json/getTiUserQianListById")
    public
    @ResponseBody
    String getTiUserQianListById(Long id) {
        AjaxBean<TiUserQianList> ajaxBean = new AjaxBean<TiUserQianList>();
        TiUserQianList obj = tiUserQianListService.getById(id);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(obj);
        return this.ajaxJson(ajaxBean);
    }

    @RequestMapping("json/findTiUserQianListsByPage")
    public
    @ResponseBody
    String findTiUserQianListsByPage(TiUserQianList searchCondition, PageInfo<TiUserQianList> pager) {
        AjaxBean<PageInfo<TiUserQianList>> ajaxBean = new AjaxBean<PageInfo<TiUserQianList>>();
        pager = tiUserQianListService.findList(searchCondition, pager);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(pager);
        return this.ajaxJson(ajaxBean);
    }

}
