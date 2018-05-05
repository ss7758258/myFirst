package com.xz.web.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Logsvr {
    private SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * 将信息记录到日志文件
     * @param logFile 日志文件
     * @param mesInfo 信息
     * @throws IOException
     */
    public void logMsg(File logFile, String mesInfo) throws IOException{
        if(logFile == null) {
            throw new IllegalStateException("logFile can not be null!");
        }
        Writer txtWriter = new FileWriter(logFile,true);
        txtWriter.write(dateFormat.format(new Date()) +"\t"+mesInfo+"\n");
        txtWriter.flush();
    }

    public static void main(String[] args) throws Exception{
        final Logsvr logsvr = new Logsvr();
        final File tmpLogFile = new File("D:\\jyt_web_20171027\\jyt_web\\mock.log");
        if(!tmpLogFile.exists()) {
            tmpLogFile.createNewFile();
        }
        //启动一个线程每1秒钟向日志文件写一次数据
        ScheduledExecutorService exec =
                Executors.newScheduledThreadPool(1000);
        exec.scheduleWithFixedDelay(new Runnable(){
            public void run() {
                try {
                    int i = 0;
                    while(++i < 100000) {
                        logsvr.logMsg(tmpLogFile, IdUtil.getUuid()+ IdUtil.getUuid()+ IdUtil.getUuid());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 1, TimeUnit.MILLISECONDS);
    }
}
