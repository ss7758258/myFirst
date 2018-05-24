package com.xz.web.utils;

import com.alibaba.fastjson.JSON;
import com.xz.framework.utils.JsonUtil;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class QRCodeUtil {
	private static final Logger logger = Logger.getLogger(QRCodeUtil.class);
	public static String accessTokenUri="https://api.weixin.qq.com/cgi-bin/token?";
	public static String appId="wxedc8a06ed85ce4df";
	public static String appSecret="ff2566625ebe546fe97dc36573aab8ed";
	public static String grant_type="client_credential";

	public static String qrCodeUri="https://api.weixin.qq.com/wxa/getwxacodeunlimit?";

	public static int width = 430;
//
//	public static String getQRCode(HttpServletRequest request, String access_token, String page){
//		page = "pages/onebrief/brief";
//		String returnStr = "";
//		StringBuffer url = new StringBuffer(qrCodeUri);
//		url.append("access_token=").append(access_token);
//
//		JSONObject params = new JSONObject();
//		params.put("page", page);
//		params.put("width", width);
//
//		CloseableHttpClient client = HttpClients.custom().build();
//		HttpPost post = new HttpPost(url.toString());
//		StringEntity entity = new StringEntity(params.toJSONString(), "UTF-8");
//		entity.setContentType("application/json");
//		post.setEntity(entity);
//
//		try (CloseableHttpResponse resp = client.execute(post)) {
//			String body = "";
//			int statusCode = resp.getStatusLine().getStatusCode();
//			body = EntityUtils.toString(resp.getEntity(), Charset.forName("UTF-8"));
//			if (statusCode == HttpStatus.SC_OK) {
//				InputStream inputStream = resp.getEntity().getContent();
//				JSONObject result = JSON.parseObject(body);
//				String filename = StringUtil.getUUID() + ".jpg";
//				String functionPath = "upload/qrCode";
//				String timePath = FileUtil.getTimePath();
//				String rootPath = request.getSession().getServletContext().getRealPath("");
//				FileUtil.creatFolder(rootPath + "/" + functionPath + timePath);
//
//				String absolutePath = rootPath + "/" + functionPath + timePath + filename;
//				File file = new File(absolutePath);
//				FileOutputStream out = new FileOutputStream(absolutePath);
//				byte[] buffer = new byte[8192];
//				int bytesRead = 0;
//				while((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
//					out.write(buffer, 0, bytesRead);
//				}
//				out.flush();
//				out.close();
//
//				OSSUtil oss = new OSSUtil();
//				returnStr = oss.fileToOss(file);
//			}
//		} catch (IOException e) {
//			logger.error(e.getMessage());
//		}
//		return returnStr;
//	}

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
