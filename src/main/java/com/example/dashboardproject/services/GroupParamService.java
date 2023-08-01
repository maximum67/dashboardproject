package com.example.dashboardproject.services;

import com.example.dashboardproject.models.*;
import com.example.dashboardproject.repositories.GroupParamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupParamService {

    private final GroupParamRepository groupParamRepository;

    public List<GroupParam> list() {
        return groupParamRepository.findAll();
    }

    public void updateGroupParam(GroupParam groupParam) {
        groupParamRepository.save(groupParam);
    }

    public void deleteGroupParam(Long id) {
        groupParamRepository.deleteById(id);
    }

    public GroupParam getById(Long id) {
        if (groupParamRepository.findAll().isEmpty() || !groupParamRepository.existsById(id)) {
            GroupParam groupParam = new GroupParam();
            groupParam.setGroupName("Демо");
            groupParam.setId(1000000000L);
            return groupParam;
        } else {
            return groupParamRepository.getReferenceById(id);
        }
    }
    public List<GroupParam> findAllByHidden(){
        List<GroupParam> groupParams = groupParamRepository.findAll();
        List<GroupParam> groupParamList = new ArrayList<>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (GroupParam groupParam: groupParams) {
            List<HiddenSettingGroup> hiddenSettingGroups = groupParam.getHiddenSettingGroupList();
            for (HiddenSettingGroup hiddenSettingGroup: hiddenSettingGroups) {
                if (user.getId() == hiddenSettingGroup.getUser().getId() && hiddenSettingGroup.getIsHidden()) {
                    groupParamList.add(groupParam);
                }
            }
        }
        return groupParamList;
    }

    public void changeGroupParam(GroupParam groupParam, Map<String,String> mapParams, List<DashboardParam> dashboardParams){
//        if (!groupParam.getDashboardParams().isEmpty()){ groupParam.getDashboardParams().clear();}
        List<DashboardParam> dashboardParamList = new ArrayList<>();
        for(String key: mapParams.keySet()) {
            if (key.equals("groupName")) {
                groupParam.setGroupName(mapParams.get(key));
            } else if (key.matches("\\d+")) {
                for (DashboardParam dashboardParam : dashboardParams) {
                    if (dashboardParam.getId() == Integer.parseInt(key)) {
//                        System.out.println("yes");
  //                      System.out.println(dashboardParam.getName());
                        dashboardParamList.add(dashboardParam);
                    }else{
    //                    System.out.println("no");
      //                  System.out.println(dashboardParam.getName());
                    }
                }
            }
        }
//        for (DashboardParam par: dashboardParamList) {
//            System.out.println(par.getName());
//        }
        groupParam.setDashboardParams(dashboardParamList);
//        System.out.println(dashboardParamList.size());
        groupParamRepository.save(groupParam);
 //       List <GroupParam> groupParams1=groupParamRepository.findAll();
 //       for (GroupParam grparam:   groupParams1) {
 //           List<DashboardParam> dashboardParamList1 = grparam.getDashboardParams();
 //           for (DashboardParam dash: dashboardParamList1) {
 //               System.out.println(dash.getName());
 //           }
//            System.out.println("___________");
//        }

   }
       public List<String> getNameParamsOfGroup(GroupParam groupParam){
         List<String> nameList = new ArrayList<>();
         List<DashboardParam> dashboardParams = groupParam.getDashboardParams();

           for (DashboardParam dashboardParam: dashboardParams) {
               nameList.add(dashboardParam.getName());
 //              System.out.println(dashboardParam.getName());

           }
        return nameList;
       }

    public List<String> listParamsByGroupName(GroupParam groupParam){
         List<String> list = new LinkedList<>();
        for (DashboardParam dashboardParam: groupParam.getDashboardParams()) {
            list.add(dashboardParam.getName());
        }
        return list;
    }

    public List<Map> listParamsByGroupId(GroupParam groupParam){
        List<Map> list = new LinkedList<>();
        for (DashboardParam dashboardParam: groupParam.getDashboardParams()) {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(dashboardParam.getId()));
            map.put("icon", dashboardParam.getIcon());
           list.add(map);

        }
        return list;
    }
//    List<List> listGroupParam = new LinkedList<>();
//        for (DashboardParam dashboardParam : groupParam.getDashboardParams()) {
//        List<Map> listDashboardV1 = new LinkedList<>();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
//        Sort sort = Sort.by("date").ascending();
//        List<DashboardV1> dashboardV1s = dashboardV1repository.findAllByDashboardParam(dashboardParam, sort);
//        for (DashboardV1 dashboardV1 : dashboardV1s) {
//            Map<String, String> map = new HashMap<>();
//            map.put("date", formatter.format(dashboardV1.getDate()));
//            map.put("value", dashboardV1.getValue());
//            listDashboardV1.add(map);
//        }
//        int end = listDashboardV1.size();
//        if (p < listDashboardV1.size()) {
//            listGroupParam.add(listDashboardV1.subList(end - p, end));
//        }else {
//            listGroupParam.add(listDashboardV1);
//        }
//    }
//        return listGroupParam;
//}


}
