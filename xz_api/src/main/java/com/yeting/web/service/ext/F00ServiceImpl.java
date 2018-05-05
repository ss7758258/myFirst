package com.yeting.web.service.ext;

import com.yeting.framework.bean.ajax.YTResponseBody;
import com.yeting.framework.bean.enums.AjaxStatus;
import com.yeting.framework.exception.MessageException;
import com.yeting.framework.utils.JsonUtil;
import com.yeting.framework.utils.MD5;
import com.yeting.framework.utils.StringUtil;
import com.yeting.web.service.WeixinUserService;
import com.yeting.web.service.redis.RedisService;
import com.yeting.web.utils.AuthToken;
import com.yeting.web.utils.WechatUtil;
import com.yeting.web.vo.LoginVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class F00ServiceImpl implements F00Service {

    private static final Logger logger = Logger.getLogger(F00ServiceImpl.class);
    @Autowired
    private RedisService redisService;
    @Autowired
    private WeixinUserService weixinUserService;

    @Override
    public YTResponseBody<LoginVo> login(String code) throws MessageException {
        YTResponseBody<LoginVo> response = new YTResponseBody<LoginVo>();
        AuthToken authToken = WechatUtil.getAuthToken(code);
        if(null==authToken|| StringUtil.isEmpty(authToken.getOpenid()))
        {
            logger.error("authToken:"+ JsonUtil.serialize(authToken));
            response.setStatus(AjaxStatus.ERROR);
            response.setMessage("登录失败!");
            return response;
        }
        String token = MD5.MD5(code);
        try
        {
            redisService.set("TOKEN:"+token, JsonUtil.serialize(authToken));
        }catch (Exception e)
        {
            logger.error("setAuthToken to redis error", e);
            response.setStatus(AjaxStatus.ERROR);
            response.setMessage("登录失败!");
            return response;
        }
        LoginVo vo = new LoginVo();
        vo.setCode(code);
        vo.setOpenId(authToken.getOpenid());
        vo.setToken(token);
        response.setStatus(AjaxStatus.SUCCESS);
        response.setData(vo);
        return response;
    }
}
