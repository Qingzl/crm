package com.javalearning.settings.dao;

import com.javalearning.settings.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    User queryByActAndPwd(@Param("loginAct_p") String loginAct,@Param("loginPwd_p") String loginPwd);
    List<User> getAll();
}
