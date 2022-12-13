package com.afa1997.electrournapi.svc;

import com.afa1997.electrournapi.mod.Turn;

import java.util.List;

public interface Svc_Turn {
    List<Turn> findAll();

    Turn findById(int param_turn_id);
    Turn save(Turn param_turn_data);
}