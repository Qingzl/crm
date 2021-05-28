package com.javalearning.workbench.activity.dao;

import com.javalearning.workbench.activity.domain.ActivityRemark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityRemarkDao {
    int queryById(String[] activityIds);

    int deleteById(String[] activityIds);

    int deleteSingleById(@Param("id_p") String id);

    int update(ActivityRemark activityRemark);

    List<ActivityRemark> getByActivityId(@Param("aid_p") String aid);

    int add(ActivityRemark activityRemark);
}
