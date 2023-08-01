package com.example.dashboardproject.services;

import com.example.dashboardproject.models.*;
import com.example.dashboardproject.repositories.GroupTypeLeneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GroupTypeLineService {

    private final GroupTypeLeneRepository groupTypeLeneRepository;

    public LineSettingGroup getById(Long id) {
        if (groupTypeLeneRepository.findAll().isEmpty()) {
            LineSettingGroup lineSettingGroup = new LineSettingGroup();
            lineSettingGroup.setTypeLine(TypeLine.LINE);
            lineSettingGroup.setId(1L);
            return lineSettingGroup;
        } else {
            return groupTypeLeneRepository.getById(id);
        }
    }

    public void updateGroupTypeline(LineSettingGroup lineSettingGroup) {
        groupTypeLeneRepository.save(lineSettingGroup);
    }

    public String getTypeLineByUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<LineSettingGroup> lineSettingGroups = groupTypeLeneRepository.findAll();
        if (!lineSettingGroups.isEmpty()) {
            for (LineSettingGroup lineSettingGroup : lineSettingGroups) {
                if (Objects.equals(lineSettingGroup.getUser().getId(), user.getId())) {
                    return switch (lineSettingGroup.getTypeLine()) {
                        case BAR -> "Гистограмма";
                        case BAR3D -> "Гистограмма 3D";
                        case TEXT -> "Текст";
                        case PIE -> "Круг";
                        case DONUT3D-> "Кольцо 3D";
                        case LINE_AREA -> "Область";
                        case LINE_REGRESS -> "Область с регрессом";
                        default -> "Линия";
                    };
                }
            }
        }
        return "Диаграмма";
    }

    public LineSettingGroup getTypeLineByUserAndParam(GroupParam groupParam) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<LineSettingGroup> lineSettingGroups = groupTypeLeneRepository.findAll();
        if (!lineSettingGroups.isEmpty()) {
            for (LineSettingGroup lineSettingGroup : lineSettingGroups) {
                if ((Objects.equals(lineSettingGroup.getGroupParam().getId(), groupParam.getId())) &&
                        (Objects.equals(lineSettingGroup.getUser().getId(), user.getId()))) {
                    return lineSettingGroup;
                }
            }
            LineSettingGroup lineSettingGroup1 = new LineSettingGroup();
            lineSettingGroup1.setGroupParam(groupParam);
            lineSettingGroup1.setUser(user);
            return lineSettingGroup1;
        } else {
            LineSettingGroup lineSettingGroup2 = new LineSettingGroup();
            lineSettingGroup2.setGroupParam(groupParam);
            lineSettingGroup2.setUser(user);
            return lineSettingGroup2;
        }
    }
}
