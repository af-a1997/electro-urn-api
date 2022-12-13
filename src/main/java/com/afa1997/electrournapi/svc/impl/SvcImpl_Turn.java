package com.afa1997.electrournapi.svc.impl;

import com.afa1997.electrournapi.mod.Turn;
import com.afa1997.electrournapi.repos.Repo_Turn;
import com.afa1997.electrournapi.svc.Svc_Turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SvcImpl_Turn implements Svc_Turn {
    @Autowired
    Repo_Turn repo_t;

    @Override
    public List<Turn> findAll() {
        return repo_t.findAll();
    }

    @Override
    public Turn findById(int param_turn_id) {
        return repo_t.findById(param_turn_id).get();
    }

    @Override
    public Turn save(Turn param_turn_data) {
        return repo_t.save(param_turn_data);
    }
}
