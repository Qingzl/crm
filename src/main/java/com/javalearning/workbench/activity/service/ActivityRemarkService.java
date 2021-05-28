package com.javalearning.workbench.activity.service;

import com.javalearning.workbench.activity.domain.ActivityRemark;
import com.javalearning.workbench.vo.Page;

import java.util.List;

public interface ActivityRemarkService {
    List<ActivityRemark> getByActivityId(String aid);

    Page<ActivityRemark> pagequery(String pagesize,String pageno,String aid);

    Boolean add(ActivityRemark activityRemark);

    Boolean deleteSingleById(String rid);

    boolean update(ActivityRemark a);
}
