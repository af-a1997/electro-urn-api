package com.afa1997.electrournapi.svc;

import com.afa1997.electrournapi.mod.Candidates;

import java.util.List;

public interface Svc_Candidates {
    List<Candidates> findAll();

    Candidates findById(int param_candidate_id);
    Candidates save(Candidates param_candidate_data);
}