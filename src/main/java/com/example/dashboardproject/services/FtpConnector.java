package com.example.dashboardproject.services;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;


public class FtpConnector {

    private static final Logger logger = Logger.getLogger(FtpConnector.class);

    public FTPClient connect(HashMap<String,String> map){

        // создаем экземпляр FTPClient
        FTPClient ftpClient = new FTPClient();
        try {
            // устанавливаем соединение с конкретным host
            // port.
            ftpClient.connect(map.get("host"), Integer.parseInt(map.get("port")));

            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                 logger.info ("Нет подключения к серверу FTP. Код ошибки: "+ replyCode);
                ftpClient.disconnect();
            }
            // входим на ftp server под именем и паролем
            boolean success
                    = ftpClient.login(map.get("login"), map.get("password"));
            if (!success) {
                logger.info("Неверный логин или пароль для подключения к FTP серверу");
                ftpClient.disconnect();
            }
            // назначим тип файла в соответствии с сервером
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE );
            ftpClient.enterLocalPassiveMode();
        }
        catch (UnknownHostException E) {
            logger.info("Сервер FTP не найден");
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }
        return ftpClient;
    }
    public String checkConnection(HashMap<String,String> map) throws IOException{

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(map.get("host"), Integer.parseInt(map.get("port")));
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return  "Ошибка операции. Код ошибки: "+ replyCode;
            }
            boolean success
                    = ftpClient.login(map.get("login"), map.get("password"));
            if (!success) {
                ftpClient.disconnect();
                return "Не верный логин или пароль";
            }else{
                ftpClient.disconnect();
                return "Успешное подключение к серверу";
            }
        }
        catch (UnknownHostException E) {
           return  "Не найден сервер";
        }
        catch (IOException e) {
           return e.getMessage();
       }
    }
}
