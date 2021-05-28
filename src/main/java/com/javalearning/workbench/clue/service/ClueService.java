package com.javalearning.workbench.clue.service;

import com.javalearning.workbench.clue.domain.Clue;

public interface ClueService {
    Boolean save(Clue c);

    Clue queryById(String cid);
}
