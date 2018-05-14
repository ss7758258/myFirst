package com.xz.web.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xz.framework.utils.JsonUtil;
import com.xz.framework.utils.MD5;
import com.xz.framework.utils.StringUtil;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class QRCodeUtil {
	private static final Logger logger = Logger.getLogger(QRCodeUtil.class);
	public static String accessTokenUri="https://api.weixin.qq.com/cgi-bin/token?";
	public static String appId="wxedc8a06ed85ce4df";
	public static String appSecret="ff2566625ebe546fe97dc36573aab8ed";
	public static String grant_type="client_credential";

	public static String qrCodeUri="https://api.weixin.qq.com/wxa/getwxacodeunlimit?";
	public static String scene = "123";
	/**
	 * 下面三个参数可不传
	 */
	// width	Int	430	二维码的宽度
	public static int width = 430;
	// auto_color	Bool	false	自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调
	public static Boolean auto_color = false;
	// line_color	Object {"r":"0","g":"0","b":"0"}	auth_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"},十进制表示
	public static Object line_color = "{\"r\":\"0\",\"g\":\"0\",\"b\":\"0\"}";
	// is_hyaline	Bool	false	是否需要透明底色， is_hyaline 为true时，生成透明底色的小程序码
	public static Boolean is_hyaline = false;

	public static String getQRCode(HttpServletRequest request, String access_token, String page){
		page = "pages/onebrief/brief";
		String returnStr = "";
		StringBuffer url = new StringBuffer(qrCodeUri);
		url.append("access_token=").append(access_token);

		JSONObject params = new JSONObject();
		params.put("page", page);
		params.put("width", width);
		params.put("scene", scene);

		CloseableHttpClient client = HttpClients.custom().build();
		HttpPost post = new HttpPost(url.toString());
		StringEntity entity = new StringEntity(params.toJSONString(), "UTF-8");
		entity.setContentType("application/json");
		post.setEntity(entity);

		try (CloseableHttpResponse resp = client.execute(post)) {
			String body = "";
			int statusCode = resp.getStatusLine().getStatusCode();
			body = EntityUtils.toString(resp.getEntity(), Charset.forName("UTF-8"));
			if (statusCode == HttpStatus.SC_OK) {
				InputStream inputStream = resp.getEntity().getContent();
				JSONObject result = JSON.parseObject(body);
				String filename = StringUtil.getUUID() + ".jpg";
				String functionPath = "upload/qrCode";
				String timePath = FileUtil.getTimePath();
				String rootPath = request.getSession().getServletContext().getRealPath("");
				FileUtil.creatFolder(rootPath + "/" + functionPath + timePath);

				String absolutePath = rootPath + "/" + functionPath + timePath + filename;
				File file = new File(absolutePath);
				FileOutputStream out = new FileOutputStream(absolutePath);
				byte[] buffer = new byte[8192];
				int bytesRead = 0;
				while((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
					out.write(buffer, 0, bytesRead);
				}
				out.flush();
				out.close();

				OSSUtil oss = new OSSUtil();
				returnStr = oss.fileToOss(file);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return returnStr;
	}

	public static AccessToken getAccessToken(){
		AccessToken vo = null;
		try {
			StringBuffer url = new StringBuffer(accessTokenUri);
			url.append("appid=").append(appId);
			url.append("&secret=").append(appSecret);
			url.append("&grant_type=").append(grant_type);
			HttpURLConnection conn = HttpClientUtil.CreatePostHttpConnection(url.toString());
			InputStream input = null;
			if (conn.getResponseCode() == 200) {
				input = conn.getInputStream();
			} else {
				input = conn.getErrorStream();
			}
			System.out.println("weixin getAccessToken url:"+ url);
			vo = JSON.parseObject(new String(HttpClientUtil.readInputStream(input),"utf-8"),AccessToken.class);
			System.out.println("weixin getAccessToken data:"+ JsonUtil.serialize(vo));
		} catch (Exception e) {
			logger.error("getAccessToken error", e);
		}
		return vo;
	}
}
