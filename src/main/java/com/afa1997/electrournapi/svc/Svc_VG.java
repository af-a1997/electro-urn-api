package com.afa1997.electrournapi.svc;

import com.afa1997.electrournapi.mod.VoteReg;

import java.util.List;

public interface Svc_VG {
    List<VoteReg> findAll();

    VoteReg findById(int param_id_vg);
    VoteReg findTop1ByCandidate_types(int param_id_ct);
    VoteReg save(VoteReg param_vg_data);
    VoteReg countTop1ByCandidate_to();
}