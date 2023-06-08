package com.example.dashboardproject.fileParsing;

import com.example.dashboardproject.models.DashboardV1;
import com.example.dashboardproject.models.FtpSetting;
import com.example.dashboardproject.repositories.DashboardV1repository;
import com.example.dashboardproject.services.V1service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ParsingWork {

     public void parsingFile(FtpSetting ftpSetting, V1service v1service) throws IOException {

        String[] strings = ftpSetting.getFilename().split("/");
        String filename = strings[strings.length-1];
        ExcelReader excelReader = new ExcelReader();
        Map<Integer, List<Object>> map = excelReader.read(filename);
        int trDate = ftpSetting.getTrDate();
        int thDate = ftpSetting.getThDate();
        int trValue = ftpSetting.getTrValue();
        int thValue = ftpSetting.getThValue();
//        for (int i = 0; i < map.size(); i++) {
//            List<Object> list = map.get(i);
//            for (int j = 0; j < list.size(); j++) {
//                System.out.print(list.get(j));
//                System.out.print("    ");
//            }
//            System.out.println();
//        }
//        System.out.println("____________________________________________");
//        System.out.print("Получаем нужную ячейку дата ");
//        System.out.println(map.get(trDate-1).get(thDate-1));
//        System.out.println("____________________________________________");
//        System.out.print("Получаем нужную ячейку значение параметра ");
//        System.out.println(map.get(trValue-1).get(thValue-1));
        DashboardV1 dashboardV1 = new DashboardV1();
        dashboardV1.setDate((String) map.get(trDate-1).get(thDate-1));
        dashboardV1.setValue((String) map.get(trValue-1).get(thValue-1));
        dashboardV1.setDashboardParam(ftpSetting.getDashboardParam());
        v1service.updateDashboardV1(dashboardV1);
   }

}
