package com.afa1997.electrournapi.svc;

import com.afa1997.electrournapi.mod.VoteReg;

import java.util.List;

public interface Svc_VG {
    List<VoteReg> findAll();

    VoteReg findById(int param_id_vg);
    VoteReg save(VoteReg param_vg_data);
}