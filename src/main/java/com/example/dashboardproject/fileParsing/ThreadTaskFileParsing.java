package com.example.dashboardproject.fileParsing;

import com.example.dashboardproject.models.FtpSetting;
import com.example.dashboardproject.services.V1service;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;


public class ThreadTaskFileParsing extends Thread{

    private FtpSetting ftpSetting;
    private V1service v1service;
    private static final Logger logger = Logger.getLogger(ThreadTaskFileParsing.class);

    public ThreadTaskFileParsing(String name) {
    super(name);
    }
    public ThreadTaskFileParsing(){
    }

    public ThreadTaskFileParsing(FtpSetting ftpSetting, V1service v1service) {
        this.ftpSetting = ftpSetting;
        this.v1service = v1service;
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }

    @Override
    public boolean isInterrupted() {
        return super.isInterrupted();
    }

    @Override
    public void run() {

        FtpDownloadFiles ftpDownloadFiles = new FtpDownloadFiles();
        ParsingWork parsingWork = new ParsingWork();

        LocalTime localTime = ftpSetting.getTimeTask();
        long timesleep = 100000;
        long timestart = localTime.getMinute()*60000 + localTime.getHour()*3600000;
        Date date = new Date();
        if (date.getTime() % 86400000 + 10800000 < timestart) {
            timesleep = timestart - (date.getTime() % 86400000 + 10800000);
        } else {
            timesleep = 86400000 - (date.getTime() % 86400000 + 10800000) + timestart;
        }

         while (!currentThread().isInterrupted()){
            try {
//                Thread.sleep(timesleep);
                Thread.sleep(3000);
                ftpDownloadFiles.ftpDownloadFiles(ftpSetting);
                parsingWork.parsingFile(ftpSetting, v1service);
            } catch (InterruptedException e) {
                logger.info("Поток остановлен");
                e.printStackTrace();
                break;
            } catch (IOException e) {
                logger.info(e);
                e.printStackTrace();
            }
         }
   }

}
