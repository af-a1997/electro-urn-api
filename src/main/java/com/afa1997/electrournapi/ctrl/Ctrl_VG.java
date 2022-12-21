package com.afa1997.electrournapi.ctrl;

import com.afa1997.electrournapi.mod.*;
import com.afa1997.electrournapi.repos.*;

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
    Repo_Candidates repo_cndt;

    @Autowired
    Repo_CandidateTypes repo_cts;

    @Autowired
    Repo_Turn repo_t;

    @Autowired
    Repo_Voters repo_vt;

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
        // Get candidate type ID and candidate ID respectively.
        int vg_id_ct = get_vg_json_in.getId_ct();
        int vg_id_cn = get_vg_json_in.getCandidate_id();
        int vg_id_vi = get_vg_json_in.getVoter_id();

        // The list of turns is read to detect which turns is active further below. If no turns are active, users votes are rejected.
        List<Turn> list_of_turns = repo_t.findAll();
        Optional<Candidates> opt_cndt_data = repo_cndt.findById(vg_id_cn);
        Optional<CandidateTypes> opt_ct_data = repo_cts.findById(vg_id_ct);
        Optional<Voters> opt_vt_data = repo_vt.findById(vg_id_vi);
        VoteReg i_votereg = new VoteReg();
        Candidates i_cndt = new Candidates();
        CandidateTypes i_ct = new CandidateTypes();

        // TODO: Test to get data from other tables and set values to [i_votereg], might want to delete the print line commands later on.
        if(opt_cndt_data.isPresent() && opt_ct_data.isPresent() && opt_vt_data.isPresent()) {
            System.out.println("Candidate type = " + vg_id_ct + " (" + opt_ct_data.get().getName() + ")");
            System.out.println("Candidate ID = " + vg_id_cn + " (" + opt_cndt_data.get().getFirst_name() + " " + opt_cndt_data.get().getLast_name() + ")");
            System.out.println("User " + vg_id_vi + " (" + opt_vt_data.get().getFirst_name() + " " + opt_vt_data.get().getLast_name() + ") wants to submit a vote for this candidate.");

            // Gets candidate, candidate type and voter data respectively, to set the foreign key values on the DDBB:
            i_votereg.setChosen_role(opt_ct_data.get());
            i_votereg.setVoted_candidate(opt_cndt_data.get());
            i_votereg.setCndt_voter(opt_vt_data.get());
        }
        else return ResponseEntity.badRequest().build();

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