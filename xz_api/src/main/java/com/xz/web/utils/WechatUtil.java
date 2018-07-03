package com.xz.web.utils;

import com.alibaba.fastjson.JSON;
import com.xz.framework.utils.JsonUtil;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class WechatUtil {
	private static final Logger logger = Logger.getLogger(WechatUtil.class);
	public static String uri="https://api.weixin.qq.com/sns/jscode2session?";
	public static String appId="wxedc8a06ed85ce4df";
	public static String appSecret="ff2566625ebe546fe97dc36573aab8ed";

	public static AuthToken getAuthToken(String code){
		AuthToken vo = null;
		String output="";
		StringBuffer url = new StringBuffer(uri);
		url.append("appid=").append(appId);
		url.append("&secret=").append(appSecret);
		url.append("&js_code=").append(code);
		url.append("&grant_type=").append("authorization_code");
		InputStream input = null;
		HttpURLConnection conn = null;
		int status = 0;
		try {
			conn = HttpClientUtil.CreatePostHttpConnection(url.toString());
			status = conn.getResponseCode();
			if (status == 200) {
				input = conn.getInputStream();
			} else {
				input = conn.getErrorStream();
			}
			System.out.println("WEIXIN URL:"+ url);
			output = new String(HttpClientUtil.readInputStream(input),"utf-8");
			vo = JSON.parseObject(output,AuthToken.class);
			System.out.println("WEIXIN DATA:"+ JsonUtil.serialize(vo));
		} catch (Exception e) {
			logger.error("getAuthToken error", e);
			logger.error("output="+output);
			logger.error("status="+status);
		}
		return vo;
	}
}
