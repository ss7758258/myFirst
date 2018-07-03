package com.xz.web.controller;

import com.xz.framework.common.base.AjaxBean;
import com.xz.framework.common.base.AjaxStatus;
import com.xz.framework.common.base.BaseController;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.utils.bean.BeanUtil;
import com.xz.framework.utils.date.DateUtil;
import com.xz.web.constant.Constant;
import com.xz.web.entity.TiLucky;
import com.xz.web.redis.RedisDao;
import com.xz.web.service.TiLuckyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller("tiLuckyTiLuckyController")
@RequestMapping("tiLucky")
public class TiLuckyController extends BaseController {

    @Autowired
    private TiLuckyService tiLuckyService;
    @Autowired
    private RedisDao redisDao;

    

    @RequestMapping("json/addTiLucky")
    public
    @ResponseBody
    String addTiLucky(TiLucky tiLucky) {
        AjaxBean<TiLucky> ajaxBean = new AjaxBean<TiLucky>();
        TiLucky entity = new TiLucky();
        BeanUtil.copyProperties(tiLucky, entity, Constant.NotNul);
        entity.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        int flag = tiLuckyService.add(entity);
        if(flag>0) {
            try
            {
                redisDao.del("lucky-:"+entity.getConstellationId());
                redisDao.del("luckyMore-:"+entity.getConstellationId());
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

    @RequestMapping("json/delTiLuckyById")
    public
    @ResponseBody
    String delTiLuckyById(Long id) {
        AjaxBean<Integer> ajaxBean = new AjaxBean<Integer>();
        TiLucky entity = tiLuckyService.getById(id);
        if(entity==null)
        {
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage("删除失败!");
            return this.ajaxJson(ajaxBean);
        }
        int flag = tiLuckyService.removeById(id);
        if(flag>0) {
            try
            {
                redisDao.del("lucky-:"+entity.getConstellationId());
                redisDao.del("luckyMore-:"+entity.getConstellationId());
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

    @RequestMapping("json/updateTiLuckyById")
    public
    @ResponseBody
    String updateTiLuckyById(TiLucky tiLucky) {
        AjaxBean<String> ajaxBean = new AjaxBean<String>();
        Long id = tiLucky.getId();
        if(id!=null) {
            TiLucky entity = tiLuckyService.getById(id);
            if(null==entity) {
                ajaxBean.setStatus(AjaxStatus.ERROR);
                ajaxBean.setMessage("记录不存在!");
            }else
            {
                BeanUtil.copyProperties(tiLucky,entity,Constant.NotNul);
                entity.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
                int flag = tiLuckyService.update(entity);
                if(flag>0) {
                    try
                    {
                        redisDao.del("lucky-:"+entity.getConstellationId());
                        redisDao.del("luckyMore-:"+entity.getConstellationId());
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

    @RequestMapping("json/getTiLuckyById")
    public
    @ResponseBody
    String getTiLuckyById(Long id) {
        AjaxBean<TiLucky> ajaxBean = new AjaxBean<TiLucky>();
        TiLucky obj = tiLuckyService.getById(id);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(obj);
        return this.ajaxJson(ajaxBean);
    }

    @RequestMapping("json/findTiLuckysByPage")
    public
    @ResponseBody
    String findTiLuckysByPage(TiLucky searchCondition, PageInfo<TiLucky> pager) {
        AjaxBean<PageInfo<TiLucky>> ajaxBean = new AjaxBean<PageInfo<TiLucky>>();
        pager = tiLuckyService.findList(searchCondition, pager);
        ajaxBean.setStatus(AjaxStatus.SUCCESS);
        ajaxBean.setData(pager);
        return this.ajaxJson(ajaxBean);
    }

}
