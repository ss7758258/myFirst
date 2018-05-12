package com.xz.web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xz.web.entity.TcQianYanUrl;
import com.xz.web.service.TcQianYanUrlService;
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

@org.springframework.stereotype.Controller("tcQianYanUrlTcQianYanUrlController")
@RequestMapping("tcQianYanUrl")
public class TcQianYanUrlController extends BaseController {

    @Autowired
    private TcQianYanUrlService tcQianYanUrlService;

    

    @RequestMapping("json/addTcQianYanUrl")
    public
    @ResponseBody
    String addTcQianYanUrl(TcQianYanUrl tcQianYanUrl) {
        AjaxBean<TcQianYanUrl> ajaxBean = new AjaxBean<TcQianYanUrl>();
        TcQianYanUrl entity = new TcQianYanUrl();
        BeanUtil.copyProperties(tcQianYanUrl, entity, Constant.NotNul);
        entity.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        int flag = tcQianYanUrlService.add(entity);
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

    @RequestMapping("json/delTcQianYanUrlById")
    public
    @ResponseBody
    String delTcQianYanUrlById(Long id) {
        AjaxBean<Integer> ajaxBean = new AjaxBean<Integer>();
        int flag = tcQianYanUrlService.removeById(id);
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

    @RequestMapping("json/updateTcQianYanUrlById")
    public
    @ResponseBody
    String updateTcQianYanUrlById(TcQianYanUrl tcQianYanUrl) {
        AjaxBean<String> ajaxBean = new AjaxBean<String>();
        Long id = tcQianYanUrl.getId();
        if(id!=null) {
            TcQianYanUrl entity = tcQianYanUrlService.getById(id);
            if(null==entity) {
                ajaxBean.setStatus(AjaxStatus.ERROR);
                ajaxBean.setMessage("记录不存在!");
            }else
            {
                BeanUtil.copyProperties(tcQianYanUrl,entity,Constant.NotNul);
                entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
                int flag = tcQianYanUrlService.update(entity);
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

    @RequestMapping("json/getTcQianYanUrlById")
    public
    @ResponseBody
    String getTcQianYanUrlById(Long id) {
        AjaxBean<TcQianYanUrl> ajaxBean = new AjaxBean<TcQianYanUrl>();
        TcQianYanUrl obj = tcQianYanUrlService.getById(id);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(obj);
        return this.ajaxJson(ajaxBean);
    }

    @RequestMapping("json/findTcQianYanUrlsByPage")
    public
    @ResponseBody
    String findTcQianYanUrlsByPage(TcQianYanUrl searchCondition, PageInfo<TcQianYanUrl> pager) {
        AjaxBean<PageInfo<TcQianYanUrl>> ajaxBean = new AjaxBean<PageInfo<TcQianYanUrl>>();
        pager = tcQianYanUrlService.findList(searchCondition, pager);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(pager);
        return this.ajaxJson(ajaxBean);
    }

}
