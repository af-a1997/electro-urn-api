package com.afa1997.electrournapi.svc;

import com.afa1997.electrournapi.mod.Voters;

import java.util.List;

public interface Svc_Voters {
    List<Voters> findAll();

    Voters findById(int param_id_voter);
    Voters save(Voters param_voter_data);
}