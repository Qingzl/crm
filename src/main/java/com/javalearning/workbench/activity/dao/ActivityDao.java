package com.javalearning.workbench.activity.dao;

import com.javalearning.workbench.activity.domain.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityDao {

    int add(Activity activity);

    List<Activity> getByCondition(@Param("s_name_p") String s_name,@Param("s_owner_p") String s_owner,@Param("s_startDate_p") String s_startDate,@Param("s_endDate_p") String s_endDate);

    int delete(String[] activityIds);

    Activity getById(@Param("id_p") String id);

    int update(Activity activity);

    //线索模块相关
    List<Activity> getByClueId(String cid);

    List<Activity> getNotBind(@Param("ids") String[] id,@Param("name_p") String name);
}
