package com.xz.web.controller;
import com.xz.framework.common.base.AjaxBean;
import com.xz.framework.common.base.AjaxStatus;
import com.xz.framework.common.base.BaseController;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.utils.bean.BeanUtil;
import com.xz.framework.utils.date.DateUtil;
import com.xz.framework.utils.files.FileUtil;
import com.xz.framework.utils.string.StringUtil;
import com.xz.web.constant.Constant;
import com.xz.web.entity.TiYanList;
import com.xz.web.redis.RedisDao;
import com.xz.web.service.TiYanListService;
import com.xz.web.utils.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@org.springframework.stereotype.Controller("tiYanListTiYanListController")
@RequestMapping("tiYanList")
public class TiYanListController extends BaseController {

    @Autowired
    private TiYanListService tiYanListService;
    @Autowired
    private RedisDao redisDao;
    @Resource
    private OSSUtil ossUtil;


    @RequestMapping("json/prevPicUpload")
    public
    @ResponseBody
    String prevPicUpload(@RequestParam(name="file",required=false) MultipartFile prevPic) {
        AjaxBean<String> ajaxBean = new AjaxBean<String>();
        if(prevPic==null|| StringUtil.isEmpty(prevPic.getOriginalFilename()))
        {
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage("文件为空!");
            return this.ajaxJson(ajaxBean);
        }
        try {
            String filePrevPic = FileUtil.uploadFile("tiYanList", this.getRequest(), prevPic,ossUtil);
            ajaxBean.setStatus(AjaxStatus.SUCCESS);
            ajaxBean.setMessage("上传成功!");
            ajaxBean.setData(filePrevPic);
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


    @RequestMapping("json/addTiYanList")
    public
    @ResponseBody
    String addTiYanList(TiYanList tiYanList) {
        AjaxBean<TiYanList> ajaxBean = new AjaxBean<TiYanList>();
        TiYanList entity = new TiYanList();
        BeanUtil.copyProperties(tiYanList, entity, Constant.NotNul);
        entity.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        int flag = tiYanListService.add(entity);
        if(flag>0) {
            try
            {
                redisDao.del("everyDayWord-:" + entity.getConstellationId());
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            ajaxBean.setStatus(AjaxStatus.SUCCESS);
            ajaxBean.setMessage("增加成功!");
        }else
        {
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage("增加失败!");
        }
        return this.ajaxJson(ajaxBean);
    }

    @RequestMapping("json/delTiYanListById")
    public
    @ResponseBody
    String delTiYanListById(Long id) {
        AjaxBean<Integer> ajaxBean = new AjaxBean<Integer>();
        TiYanList entity = tiYanListService.getById(id);
        if(entity==null)
        {
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage("删除失败!");
            return this.ajaxJson(ajaxBean);
        }
        int flag = tiYanListService.removeById(id);
        if(flag>0) {
            try
            {
                redisDao.del("everyDayWord-:" + entity.getConstellationId());
            }catch (Exception e)
            {
                e.printStackTrace();
            }
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

    @RequestMapping("json/updateTiYanListById")
    public
    @ResponseBody
    String updateTiYanListById(TiYanList tiYanList) {
        AjaxBean<String> ajaxBean = new AjaxBean<String>();
        Long id = tiYanList.getId();
        if(id!=null) {
            TiYanList entity = tiYanListService.getById(id);
            if(null==entity) {
                ajaxBean.setStatus(AjaxStatus.ERROR);
                ajaxBean.setMessage("记录不存在!");
            }else
            {
                BeanUtil.copyProperties(tiYanList,entity,Constant.NotNul);
                entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
                int flag = tiYanListService.update(entity);
                if(flag>0) {
                    try
                    {
                        redisDao.del("everyDayWord-:" + entity.getConstellationId());
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
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

    @RequestMapping("json/getTiYanListById")
    public
    @ResponseBody
    String getTiYanListById(Long id) {
        AjaxBean<TiYanList> ajaxBean = new AjaxBean<TiYanList>();
        TiYanList obj = tiYanListService.getById(id);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(obj);
        return this.ajaxJson(ajaxBean);
    }

    @RequestMapping("json/findTiYanListsByPage")
    public
    @ResponseBody
    String findTiYanListsByPage(TiYanList searchCondition, PageInfo<TiYanList> pager) {
        AjaxBean<PageInfo<TiYanList>> ajaxBean = new AjaxBean<PageInfo<TiYanList>>();
        pager = tiYanListService.findList(searchCondition, pager);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(pager);
        return this.ajaxJson(ajaxBean);
    }

}
