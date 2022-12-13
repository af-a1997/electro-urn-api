package com.afa1997.electrournapi.svc.impl;

import com.afa1997.electrournapi.mod.Voters;
import com.afa1997.electrournapi.repos.Repo_Voters;
import com.afa1997.electrournapi.svc.Svc_Voters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SvcImpl_Voters implements Svc_Voters {
    @Autowired
    Repo_Voters repo_vt;

    @Override
    public List<Voters> findAll() {
        return repo_vt.findAll();
    }

    @Override
    public Voters findById(int param_id_voter) {
        return repo_vt.findById(param_id_voter).get();
    }

    @Override
    public Voters save(Voters param_voter_data) {
        return repo_vt.save(param_voter_data);
    }
}