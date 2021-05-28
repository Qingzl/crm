package com.javalearning.dic.dao;

import com.javalearning.dic.domain.Dic_value;

import java.util.List;

public interface Dic_valueDao {
    List<Dic_value> queryByTypecode(String tc);
}
