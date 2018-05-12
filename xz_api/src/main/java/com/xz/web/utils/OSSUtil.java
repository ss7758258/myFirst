package com.xz.web.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.File;

public class OSSUtil {

    public static String accessKey = "AKIDvmbKwS4NmCZnxyFIdjaBYgZDdrrcliCY";
    public static String secretKey = "GI7g06ye5IG0iGnCWluqVv51mIZcOu97";
    public static String regionName = "ap-guangzhou";
    public static String bucketName = "xingzuo-1256217146";
    public String fileToOss(File file) {
        try {
            // 1 初始化用户身份信息(secretId, secretKey)
            COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
            // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
            ClientConfig clientConfig = new ClientConfig(new Region(regionName));
            // 3 生成cos客户端
            COSClient cosclient = new COSClient(cred, clientConfig);
            // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
            String bucketName = OSSUtil.bucketName;

            String fileName = IdUtil.getDefaultUuid() + "_" + file.getName();
            // 指定要上传到 COS 上的路径
            String key = "/" + fileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            return key;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void ossToFile(File file,String key) {
        try {
            COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
            // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
            ClientConfig clientConfig = new ClientConfig(new Region(regionName));
            // 3 生成cos客户端
            COSClient cosclient = new COSClient(cred, clientConfig);
            // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
            String bucketName = OSSUtil.bucketName;
            File downFile = new File("src/test/resources/mydown.txt");
            // 指定要下载的文件所在的 bucket 和路径
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
            ObjectMetadata downObjectMeta = cosclient.getObject(getObjectRequest, downFile);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void removeKeyFromOss(String key) {
        try {
            COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
            // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
            ClientConfig clientConfig = new ClientConfig(new Region(regionName));
            // 3 生成cos客户端
            COSClient cosclient = new COSClient(cred, clientConfig);
            // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
            String bucketName = OSSUtil.bucketName;
            cosclient.deleteObject(bucketName, key);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}


