package com.afa1997.electrournapi.ctrl;

import com.afa1997.electrournapi.mod.Voters;
import com.afa1997.electrournapi.repos.Repo_Voters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class Ctrl_Voters {
    @Autowired
    Repo_Voters repo_vt;

    @RequestMapping(value = "/voter/{id_voter}", method = RequestMethod.GET)
    public ResponseEntity<Voters> getVoterData(@PathVariable(value = "id_voter") int param_id_voter){
        Optional<Voters> voter_data = repo_vt.findById(param_id_voter);

        if(voter_data.isPresent())
            return ResponseEntity.ok().body(voter_data.get());
        else
            return ResponseEntity.notFound().build();
    }
}
