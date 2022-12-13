package com.afa1997.electrournapi.svc.impl;

import com.afa1997.electrournapi.mod.CandidateTypes;
import com.afa1997.electrournapi.repos.Repo_CandidateTypes;
import com.afa1997.electrournapi.svc.Svc_CandidateTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SvcImpl_CandidateTypes implements Svc_CandidateTypes {
    @Autowired
    Repo_CandidateTypes repo_cts;

    @Override
    public List<CandidateTypes> findAll() {
        return repo_cts.findAll();
    }

    @Override
    public CandidateTypes findById(int param_ct_id) {
        return repo_cts.findById(param_ct_id).get();
    }

    @Override
    public CandidateTypes save(CandidateTypes param_ct_data) {
        return repo_cts.save(param_ct_data);
    }
}
