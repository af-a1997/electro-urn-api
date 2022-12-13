package com.afa1997.electrournapi.svc.impl;

import com.afa1997.electrournapi.mod.Candidates;
import com.afa1997.electrournapi.repos.Repo_Candidates;
import com.afa1997.electrournapi.svc.Svc_Candidates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SvcImpl_Candidates implements Svc_Candidates {
    @Autowired
    Repo_Candidates rp_cndt;

    @Override
    public List<Candidates> findAll() {
        return rp_cndt.findAll();
    }

    @Override
    public Candidates findById(int param_candidate_id) {
        return rp_cndt.findById(param_candidate_id).get();
    }

    @Override
    public Candidates save(Candidates param_candidate_data) {
        return rp_cndt.save(param_candidate_data);
    }
}
