package com.xz.framework.utils.files;

import com.xz.web.utils.id.IdUtil;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FileUtil {
    private static Logger logger = Logger.getLogger(FileUtil.class);
    private static String encoding = "UTF-8";

    /**
     * 上传文件
     * @param functionPath 类似admin/link/
     * @param request
     * @param uploadFile
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public static String uploadFile(String functionPath, HttpServletRequest request, MultipartFile uploadFile)
            throws IllegalStateException, IOException {
        if (!uploadFile.isEmpty()) {

            functionPath = "upload/"+functionPath;
            String fileType = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().indexOf("."));
            String filename = IdUtil.getDefaultUuid() + fileType;
            String timePath = getTimePath();
            String rootPath = request.getSession().getServletContext().getRealPath("");
            creatFolder(rootPath+"/"+functionPath+timePath);
            String absolutePath = rootPath +"/"+functionPath + timePath + filename;
            File file = new File(absolutePath);

            uploadFile.transferTo(file);
            return functionPath + timePath + filename;
        }
        return null;
    }

    /**
     * 获取当前时间路径
     * @return
     */
    public static String getTimePath(){
        Calendar now = Calendar.getInstance();
        return "/" + now.get(Calendar.YEAR) + "/" + (now.get(Calendar.MONTH) + 1) + "/"
                + now.get(Calendar.DAY_OF_MONTH)+ "/";
    }

    /**
     * 创建文件夹
     * @param path
     * @return
     */
    private static void creatFolder(String path) {
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    public static String readTxtFile(String filePath) {
        return readTxtFile(filePath, encoding);
    }

    public static void deleteTxtFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) { // 判断文件是否存在
            file.delete();
        }
    }
    public static void writeTxtFile(String filePath, String outData) {
        writeTxtFile(filePath, outData, encoding);
    }

    /**
     * 功能：Java读取txt文件的内容 步骤： 1：先获得文件句柄 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流 4：一行一行的输出。readline()。
     *
     * @param filePath
     */
    public static String readTxtFile(String filePath, String encoding) {
        String str = "";
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = "";
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    str = str + lineTxt+"\n";
                }
                read.close();
            } else {
                logger.error("找不到指定的文件-->" + filePath);
            }
        } catch (Exception e) {
            logger.error("读取文件内容出错-->" + filePath);
            e.printStackTrace();
        }
        return str;
    }

    public static boolean createFolder(String folderName) {
        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }
    public static void writeTxtFile(String filePath, String outData, String encoding) {
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(outData);
                bw.close();
            } else {
                file.createNewFile();
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(outData);
                bw.close();
            }
        } catch (Exception e) {
            logger.error("读取文件内容出错-->" + filePath);
            e.printStackTrace();
        }
    }

    public static List<String> showAllJsonFiles(File dir) {
        List<String> jsonFiles = new ArrayList<String>();
        File[] fs = dir.listFiles();
        for (int i = 0; i < fs.length; i++) {
            if (fs[i].getAbsolutePath().endsWith(".json")) {
                jsonFiles.add(fs[i].getAbsolutePath());
            }
            if (fs[i].isDirectory()) {
                try {
                    showAllJsonFiles(fs[i]);
                } catch (Exception e) {
                }
            }
        }
        return jsonFiles;
    }

    public static List<String> showAllImages(File dir) {
        List<String> jsonFiles = new ArrayList<String>();
        File[] fs = dir.listFiles();
        for (int i = 0; i < fs.length; i++) {
            if (fs[i].getAbsolutePath().endsWith(".jpg")) {
                jsonFiles.add(fs[i].getName());
            }
            if (fs[i].isDirectory()) {
                try {
                    showAllImages(fs[i]);
                } catch (Exception e) {
                }
            }
        }
        return jsonFiles;
    }

    public static byte[] File2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void byte2File(byte[] buf, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
