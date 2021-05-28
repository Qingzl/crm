package com.javalearning.workbench.clue.dao;

import com.javalearning.workbench.clue.domain.Clue;

public interface ClueDao {
    int save(Clue c);

    Clue queryById(String cid);
}
