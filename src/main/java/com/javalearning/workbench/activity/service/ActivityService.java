package com.javalearning.workbench.activity.service;


import com.javalearning.workbench.activity.domain.Activity;
import com.javalearning.workbench.vo.Page;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    Map<String, String> add(Activity activity);

    Page<Activity> pageQuery(String s_name,String s_owner,String s_startDate,String s_endDate,String pageno,String pagesize);

    Map<String,String> delete(String[] activityIds);

    Map<String,Object> getById(String id);

    Map<String,Object> update(Activity activity);

    Activity goToDetail(String aid,String owner);

    List<Activity> getActivitiesByClueId(String cid);

    List<Activity> getNotBindActivities(String[] id, String name);
}
