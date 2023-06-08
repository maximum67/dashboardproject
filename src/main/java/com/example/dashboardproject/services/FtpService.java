package com.example.dashboardproject.services;

import com.example.dashboardproject.fileParsing.ThreadTaskFileParsing;
import com.example.dashboardproject.models.DashboardV1;
import com.example.dashboardproject.models.FtpSetting;
import com.example.dashboardproject.repositories.FtpSettingRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FtpService {

    private final FtpSettingRepository ftpSettingRepository;
    private ThreadTaskFileParsing threadTaskFileParsing;
    private HashMap<Long, ThreadTaskFileParsing> map = new HashMap<>();

    public void deleteFtpSetting(Long id){ ftpSettingRepository.deleteById(id); }

    public void activateFtpSetting(Long id, boolean active, V1service v1service) {
        FtpSetting ftpSetting = ftpSettingRepository.findById(id).orElse(null);
        if (active) {
            ftpSetting.setActive(true);
            if (!map.containsKey(ftpSetting.getId())){
            threadTaskFileParsing = new ThreadTaskFileParsing(ftpSetting, v1service);
            threadTaskFileParsing.start();
            map.put(ftpSetting.getId(), threadTaskFileParsing);
            }
        }else{
            ftpSetting.setActive(false);
            if (map.containsKey(ftpSetting.getId())) {
                threadTaskFileParsing = map.get(ftpSetting.getId());
                threadTaskFileParsing.interrupt();
                map.remove(ftpSetting.getId());
            }
       }
        ftpSettingRepository.save(ftpSetting);
    }

    public void updateFtpSetting(FtpSetting ftpSetting){
        ftpSettingRepository.save(ftpSetting);
    }

    public List<FtpSetting> list(){ return ftpSettingRepository.findAll();}

    public FtpSetting getFtpSettingById(Long id){ return ftpSettingRepository.findById(id).orElse(null); }

    public void ftpDownloadFiles(FtpSetting ftpSetting) throws IOException {

        HashMap<String,String> hashMap = new HashMap<>();

//        FtpSetting ftpSetting = ftpSettingRepository.findById(id).orElse(null);
        hashMap.put("host", ftpSetting.getHost());
        hashMap.put("port", ftpSetting.getPort());
        hashMap.put("login", ftpSetting.getLogin());
        Base64.Decoder decoder = Base64.getDecoder();
        hashMap.put("password", new String(decoder.decode(ftpSetting.getPassword())));

        // создание экземпляра FtpConnector
        FtpConnector ftpConnector = new FtpConnector();

        // получаем объект ftp-клиента
        FTPClient ftpClient = ftpConnector.connect(hashMap);

        // перечислим все файлы, которые будут загружены
      //  FTPFile[] ftpFiles = ftpClient.listFiles("/123/reportBank (XLS).xls");
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
                        = ftpClient.retrieveFile("/123/"+file.getName(),
                        outputStream);
                System.out.println(isFileRetrieve);
                outputStream.close();

//                logger.info("{} file is downloaded : {}",
//                        file.getName(), isFileRetrieve);

            }
        }
    }
}
