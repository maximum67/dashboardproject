package com.example.dashboardproject.controller.fileParsing;

import com.example.dashboardproject.models.DashboardV1;
import com.example.dashboardproject.models.FtpSetting;
import com.example.dashboardproject.services.V1service;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ParsingWork {

   private static final Logger logger = Logger.getLogger(ParsingWork.class);

   public void parsingFile(FtpSetting ftpSetting, V1service v1service) throws IOException {

      String[] strings = ftpSetting.getFilename().split("/");
      String filename = strings[strings.length - 1];
      ExcelReader excelReader = new ExcelReader();
      Map<Integer, List<Object>> map = excelReader.read(filename);
      int trDate = ftpSetting.getTrDate();
      int thDate = ftpSetting.getThDate();
      int trValue = ftpSetting.getTrValue();
      int thValue = ftpSetting.getThValue();
      DashboardV1 dashboardV1 = new DashboardV1();
      try {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
         dashboardV1.setDate(LocalDate.parse((String) map.get(trDate - 1).get(thDate - 1), formatter));
      } catch (Exception e) {
         logger.info(e);
      }
      dashboardV1.setValue((String) map.get(trValue - 1).get(thValue - 1));
      dashboardV1.setDashboardParam(ftpSetting.getDashboardParam());
      v1service.updateDashboardV1(dashboardV1);
   }
}
