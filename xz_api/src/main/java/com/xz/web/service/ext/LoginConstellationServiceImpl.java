package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.enums.AjaxStatus;
import com.xz.framework.utils.JsonUtil;
import com.xz.framework.utils.MD5;
import com.xz.framework.utils.StringUtil;
import com.xz.web.bo.loginConstellation.X000Bo;
import com.xz.web.dao.redis.RedisDao;
import com.xz.web.utils.AuthToken;
import com.xz.web.utils.ResultUtil;
import com.xz.web.utils.WechatUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginConstellationServiceImpl implements LoginConstellationService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisDao redisDao;

    @Override
    public XZResponseBody<X000Bo> saveWeixinUser(String code) throws Exception {
        XZResponseBody<X000Bo> response = new XZResponseBody<X000Bo>();
        AuthToken authToken = WechatUtil.getAuthToken(code);
        if(null==authToken|| StringUtil.isEmpty(authToken.getOpenid()))
        {
            ResultUtil.returnResultLog(response, "登录失败", "authToken:"+ JsonUtil.serialize(authToken), logger);
            return response;
        }
        String token = MD5.MD5(code);
        redisDao.set("TOKEN:"+token, JsonUtil.serialize(authToken));

        X000Bo x000Bo = new X000Bo();
        x000Bo.setCode(code);
        x000Bo.setOpenId(authToken.getOpenid());
        x000Bo.setToken(token);
        response.setStatus(AjaxStatus.SUCCESS);
        response.setData(x000Bo);
        return response;
    }
}
