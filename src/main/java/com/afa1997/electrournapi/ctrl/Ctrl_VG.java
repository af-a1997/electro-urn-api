package com.afa1997.electrournapi.ctrl;

import com.afa1997.electrournapi.mod.*;
import com.afa1997.electrournapi.repos.Repo_Turn;
import com.afa1997.electrournapi.repos.Repo_VG;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class Ctrl_VG {
    @Autowired
    Repo_VG repo_votereg;

    @Autowired
    Repo_Turn repo_t;

    @RequestMapping(value = "/vote/view/{id_vg}", method = RequestMethod.GET)
    public ResponseEntity<VoteReg> getVoteRegEntryData(@PathVariable(value = "id_vg") int param_id_vg){
        Optional<VoteReg> votereg_entry = repo_votereg.findById(param_id_vg);

        if(votereg_entry.isPresent())
            return ResponseEntity.ok().body(votereg_entry.get());
        else
            return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/vote/submit", method = RequestMethod.POST)
    public ResponseEntity<VoteReg> submitVoteTo(@Validated @RequestBody GetVoteToRegData get_vg_json_in){
        List<Turn> list_of_turns = repo_t.findAll();
        VoteReg i_votereg = new VoteReg();
        Candidates i_cndt = new Candidates();
        CandidateTypes i_ct = new CandidateTypes();

        // Get candidate type ID and candidate ID respectively.
        int vg_id_ct = get_vg_json_in.getId_ct();
        int vg_id_cn = get_vg_json_in.getCandidate_id();

        System.out.println("Candidate type = " + vg_id_ct);
        System.out.println("Candidate ID = " + vg_id_cn);

        i_ct.setId_ct(vg_id_ct);
        i_cndt.setCandidate_id(vg_id_cn);

        i_votereg.setChosen_role(i_ct);
        i_votereg.setVoted_candidate(i_cndt);

        if(!list_of_turns.get(0).isIs_active() && !list_of_turns.get(1).isIs_active()){
            System.out.println("You can't vote.");

            return ResponseEntity.badRequest().build();
        }
        else if(list_of_turns.get(0).isIs_active() && !list_of_turns.get(1).isIs_active()) {
            System.out.println("Vote registered on turn 1.");

            i_votereg.setDt_vote(LocalDate.now().toString());
            i_votereg.setTurn_id(1);
        }
        else if(list_of_turns.get(1).isIs_active() && !list_of_turns.get(0).isIs_active()){
            System.out.println("Vote registered on turn 2.");

            i_votereg.setDt_vote(LocalDate.now().toString());
            i_votereg.setTurn_id(2);
        }

        repo_votereg.save(i_votereg);

        return ResponseEntity.ok().build();
    }
}
