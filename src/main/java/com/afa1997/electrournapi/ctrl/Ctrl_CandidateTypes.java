package com.afa1997.electrournapi.ctrl;

import com.afa1997.electrournapi.mod.CandidateTypes;
import com.afa1997.electrournapi.repos.Repo_CandidateTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class Ctrl_CandidateTypes {
    @Autowired
    Repo_CandidateTypes repo_cts;

    @RequestMapping(value = "/ct/{id_ct}", method = RequestMethod.GET)
    public ResponseEntity<CandidateTypes> getCTData(@PathVariable(value = "id_ct") int param_id_ct){
        Optional<CandidateTypes> ct_data = repo_cts.findById(param_id_ct);

        if(ct_data.isPresent())
            return ResponseEntity.ok().body(ct_data.get());
        else
            return ResponseEntity.notFound().build();
    }
}