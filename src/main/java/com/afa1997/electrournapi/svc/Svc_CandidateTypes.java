package com.afa1997.electrournapi.svc;

import com.afa1997.electrournapi.mod.CandidateTypes;

import java.util.List;

public interface Svc_CandidateTypes {
    List<CandidateTypes> findAll();

    CandidateTypes findById(int param_ct_id);
    CandidateTypes save(CandidateTypes param_ct_data);
}