package com.javalearning.dic.service.impl;

import com.javalearning.dic.dao.Dic_typeDao;
import com.javalearning.dic.dao.Dic_valueDao;
import com.javalearning.dic.domain.Dic_type;
import com.javalearning.dic.domain.Dic_value;
import com.javalearning.dic.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {
    private Dic_typeDao dt;
    private Dic_valueDao dv;

    @Autowired
    public DicServiceImpl(Dic_typeDao dt, Dic_valueDao dv) {
        this.dt = dt;
        this.dv = dv;
    }

    @Override
    public Map<String, Object> dicInit() {
        Map<String,Object> m = new HashMap<>();
        List<Dic_type> dic_types = dt.queryAll();
        for (Dic_type dic_type:dic_types) {
            String typeCode = dic_type.getCode();
            List<Dic_value> dic_values= dv.queryByTypecode(typeCode);
            m.put(typeCode,dic_values);
        }
        return m;
    }
}
