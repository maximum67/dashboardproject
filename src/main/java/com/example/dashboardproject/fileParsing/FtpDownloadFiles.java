package com.example.dashboardproject.fileParsing;

import com.example.dashboardproject.models.FtpSetting;
import com.example.dashboardproject.services.FtpConnector;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

public class FtpDownloadFiles {
    public void ftpDownloadFiles(FtpSetting ftpSetting) throws IOException {

        HashMap<String, String> hashMap = new HashMap<>();
        Base64.Decoder decoder = Base64.getDecoder();

//        FtpSetting ftpSetting = ftpSettingRepository.findById(id).orElse(null);
        hashMap.put("host", ftpSetting.getHost());
        hashMap.put("port", ftpSetting.getPort());
        hashMap.put("login", ftpSetting.getLogin());
        hashMap.put("password", new String(decoder.decode(ftpSetting.getPassword())));

        // создание экземпляра FtpConnector
        FtpConnector ftpConnector = new FtpConnector();

        // получаем объект ftp-клиента
        FTPClient ftpClient = ftpConnector.connect(hashMap);

        // перечислим все файлы, которые будут загружены
   //       FTPFile[] ftpFiles = ftpClient.listFiles("/123/reportBank (XLS).xls");
       FTPFile[] ftpFiles = ftpClient.listFiles(ftpSetting.getFilename());

        // установим каталог загрузки, в котором будут находиться все файлы
        // будут храниться в локальном каталоге
        String downloading_dir
                = "";

        for (FTPFile file : ftpFiles) {

            File fileObj = new File(downloading_dir

                    + file.getName());
            //   Files.createFile(fileObj.toPath());
//
            try (OutputStream outputStream
                         = new BufferedOutputStream(
                    new FileOutputStream(fileObj))) {

                // ftpclient.retrieveFile получаем файл с
                // Ftp server и записываем его в outputStream.
                boolean isFileRetrieve
                        = ftpClient.retrieveFile(ftpSetting.getFilename(),
                        outputStream);
               // System.out.println(isFileRetrieve);
                outputStream.close();

//                logger.info("{} file is downloaded : {}",
//                        file.getName(), isFileRetrieve);

            }
        }
    }
}
