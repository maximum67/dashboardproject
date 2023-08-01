package com.example.dashboardproject.services;

import com.example.dashboardproject.models.*;
import com.example.dashboardproject.repositories.HiddenSettingGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HiddenSettingGroupService {

    private final HiddenSettingGroupRepository hiddenSettingGroupRepository;

    public HiddenSettingGroup getHiddenSettingByUserAndParam(GroupParam groupParam){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<HiddenSettingGroup> hiddenSettingGroups = hiddenSettingGroupRepository.findAll();
        if (!hiddenSettingGroups.isEmpty()){
            for (HiddenSettingGroup hiddenSettingGroup: hiddenSettingGroups){
                if (Objects.equals(hiddenSettingGroup.getGroupParam().getId(), groupParam.getId()) &&
                        Objects.equals(hiddenSettingGroup.getUser().getId(), user.getId())){
                    return hiddenSettingGroup;
                }
            }
            HiddenSettingGroup hiddenSettingGroup1 = new HiddenSettingGroup();
            hiddenSettingGroup1.setGroupParam(groupParam);
            hiddenSettingGroup1.setUser(user);
            hiddenSettingGroup1.setIsHidden(true);
            hiddenSettingGroupRepository.save(hiddenSettingGroup1);
            return hiddenSettingGroup1;
        }else{
            HiddenSettingGroup hiddenSettingGroup2 = new HiddenSettingGroup();
            hiddenSettingGroup2.setGroupParam(groupParam);
            hiddenSettingGroup2.setUser(user);
            hiddenSettingGroup2.setIsHidden(true);
            hiddenSettingGroupRepository.save(hiddenSettingGroup2);
            return hiddenSettingGroup2;
        }
    }

    public void updateHiddenSettingGroup (HiddenSettingGroup hiddenSettingGroup){ hiddenSettingGroupRepository.save(hiddenSettingGroup);}


}
