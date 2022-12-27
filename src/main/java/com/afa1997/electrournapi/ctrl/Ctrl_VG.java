package com.afa1997.electrournapi.ctrl;

import com.afa1997.electrournapi.mod.*;
import com.afa1997.electrournapi.repos.*;
import com.afa1997.electrournapi.utils.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

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
        List<Turn> list_of_turns = repo_t.findAll();

        // Get candidate type ID and candidate ID respectively.
        int vg_id_ct = get_vg_json_in.getId_ct();
        int vg_id_cn = get_vg_json_in.getCandidate_id();
        int vg_id_vi = get_vg_json_in.getVoter_id();

        // Get active turn.
        int turn_no = 0;
        if(list_of_turns.get(0).isIs_active())
            turn_no = 1;
        else if(list_of_turns.get(1).isIs_active())
            turn_no = 2;

        Optional<Candidates> opt_cndt_data = repo_cndt.findById(vg_id_cn);
        Optional<CandidateTypes> opt_ct_data = repo_cts.findById(vg_id_ct);
        Optional<Voters> opt_vt_data = repo_vt.findById(vg_id_vi);

        VoteReg i_votereg = new VoteReg();

        if(opt_vt_data.isPresent()) {
            i_votereg.setCndt_voter(opt_vt_data.get());
            i_votereg.setDt_vote(LocalDate.now().toString());

            // If both candidate ID and candidate type ID coming from the payload match any existing records, sets the foreign key reference, otherwise treat the vote as a blank/null vote (user doesn't vote a particular candidate). If a value is not submitted, the first check isn't passed and the vote will be blank/null.
            if(opt_cndt_data.isPresent() && opt_ct_data.isPresent()) {
                i_votereg.setVoted_candidate(opt_cndt_data.get());
                i_votereg.setChosen_role(opt_ct_data.get());
            }
            else{
                i_votereg.setVoted_candidate(null);
                i_votereg.setChosen_role(null);
            }
        }
        else return ResponseEntity.badRequest().build();

        // Detects the active turn, if there's none, reject the vote request. By default, turn 1 is active, but you can shift through the cycle manually by sending a request with no info to </voting/shift>.
        if(turn_no == 0)        return ResponseEntity.badRequest().build();
        else if(turn_no == 1)   i_votereg.setTurn_id(1);
        else if(turn_no == 2)   i_votereg.setTurn_id(2);

        repo_votereg.save(i_votereg);

        return ResponseEntity.ok().build();
    }

    // TODO: finish implementing feature to shift turn and calculate votes.
    @RequestMapping(value = "/voting/end_turn", method = RequestMethod.GET)
    public ResponseEntity<VoteReg> finalizeVotingTurn(){
        List<VoteReg> vt_rslt = repo_votereg.countTop1ByCandidate_to(0);

        return ResponseEntity.ok().body(vt_rslt.get(0));
    }
}