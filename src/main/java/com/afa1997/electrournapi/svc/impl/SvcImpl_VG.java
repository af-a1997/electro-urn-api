package com.afa1997.electrournapi.svc.impl;

import com.afa1997.electrournapi.mod.VoteReg;
import com.afa1997.electrournapi.repos.Repo_VG;
import com.afa1997.electrournapi.svc.Svc_VG;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SvcImpl_VG implements Svc_VG {
    @Autowired
    Repo_VG repo_votereg;

    @Override
    public List<VoteReg> findAll() {
        return repo_votereg.findAll();
    }

    @Override
    public VoteReg findById(int param_id_vg) {
        return repo_votereg.findById(param_id_vg).get();
    }

    @Override
    public VoteReg save(VoteReg param_vg_data) {
        return repo_votereg.save(param_vg_data);
    }
}
