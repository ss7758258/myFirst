package com.xz.web.utils;

import com.alibaba.fastjson.JSON;
import com.xz.framework.utils.HttpClientUtil;
import com.xz.framework.utils.json.JsonUtil;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class WechatUtil {
	private static final Logger logger = Logger.getLogger(WechatUtil.class);
	public static String uri="https://api.weixin.qq.com/sns/jscode2session?";
	public static String appId="wx714d7b4ebec1c5d3";
	public static String appSecret="09777f90d5f332d23000f09b9f0414bf";

	public static AuthToken getAuthToken(String code){
		AuthToken vo = null;
		try {
			StringBuffer url = new StringBuffer(uri);
			url.append("appid=").append(appId);
			url.append("&secret=").append(appSecret);
			url.append("&js_code=").append(code);
			url.append("&grant_type=").append("authorization_code");
			HttpURLConnection conn = HttpClientUtil.CreatePostHttpConnection(url.toString());
			InputStream input = null;
			if (conn.getResponseCode() == 200) {
				input = conn.getInputStream();
			} else {
				input = conn.getErrorStream();
			}
			System.out.println("WEIXIN URL:"+ url);
			vo = JSON.parseObject(new String(HttpClientUtil.readInputStream(input),"utf-8"),AuthToken.class);
			System.out.println("WEIXIN DATA:"+ JsonUtil.serialize(vo));
		} catch (Exception e) {
			logger.error("getAuthToken error", e);
		}
		return vo;
	}
}
