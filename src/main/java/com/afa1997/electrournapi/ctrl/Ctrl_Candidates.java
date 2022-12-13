package com.afa1997.electrournapi.ctrl;

import com.afa1997.electrournapi.mod.Candidates;
import com.afa1997.electrournapi.repos.Repo_Candidates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class Ctrl_Candidates {
    @Autowired
    Repo_Candidates rp_cndt;

    // Get candidate data via HTTP GET request.
    @RequestMapping(value = "/candidate/{id_cndt}", method = RequestMethod.GET)
    public ResponseEntity<Candidates> getCandidateInfo(@PathVariable(value = "id_cndt") int param_id_cndt){
        Optional<Candidates> chosen_candidate_id = rp_cndt.findById(param_id_cndt);

        // If candidate was detected, return response with candidate data.
        if(chosen_candidate_id.isPresent())
            return ResponseEntity.ok().body(chosen_candidate_id.get());
        else
            return ResponseEntity.notFound().build();
    }

    // Save data of new candidate via POST request.
    @RequestMapping(value = "/candidate/reg", method = RequestMethod.POST)
    public ResponseEntity<Candidates> regCandidate(@Validated @RequestBody Candidates candidate_data){
        rp_cndt.save(candidate_data);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Candidates> listAllCandidates(){
        return rp_cndt.findAll();
    }
}