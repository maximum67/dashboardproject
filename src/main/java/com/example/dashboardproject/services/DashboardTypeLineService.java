package com.example.dashboardproject.services;

import com.example.dashboardproject.models.*;
import com.example.dashboardproject.repositories.DashboardTypeLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DashboardTypeLineService {

    private final DashboardTypeLineRepository dashboardTypeLineRepository;

    public LineSetting getById(Long id){
        if (dashboardTypeLineRepository.findAll().isEmpty()){
            LineSetting lineSetting = new LineSetting();
            lineSetting.setTypeLine(TypeLine.LINE);
            lineSetting.setId(1L);
            return lineSetting;
        }else{
            return dashboardTypeLineRepository.getById(id);
        }
    }

    public void updateDashboardTypeline (LineSetting lineSetting) { dashboardTypeLineRepository.save(lineSetting);}

    public String getTypeLineByUser (){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<LineSetting> lineSettings= dashboardTypeLineRepository.findAll();
        if (!lineSettings.isEmpty()){
            for (LineSetting lineSetting : lineSettings){
                if (Objects.equals(lineSetting.getUser().getId(), user.getId())){
                    return switch (lineSetting.getTypeLine()){
                        case BAR -> "Гистограмма";
                        case LINE_AREA -> "Область";
                        case LINE_REGRESS -> "Область с регрессом";
                        default -> "Линия";
                    };
                }
            }
        }
        return "Диаграмма";
    }

    public LineSetting getTypeLineByUserAndParam(DashboardParam dashboardParam){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<LineSetting> lineSettings = dashboardTypeLineRepository.findAll();
        if (!lineSettings.isEmpty()){
            for (LineSetting lineSetting : lineSettings) {
                if ((Objects.equals(lineSetting.getDashboardParam().getId(), dashboardParam.getId())) &&
                        (Objects.equals(lineSetting.getUser().getId(), user.getId()))) {
                    return lineSetting;
                }
            }
            LineSetting lineSetting1 = new LineSetting();
            lineSetting1.setDashboardParam(dashboardParam);
            lineSetting1.setUser(user);
            return lineSetting1;
        }else{
            LineSetting lineSetting2 = new LineSetting();
            lineSetting2.setDashboardParam(dashboardParam);
            lineSetting2.setUser(user);
            return lineSetting2;
        }
    }
}
