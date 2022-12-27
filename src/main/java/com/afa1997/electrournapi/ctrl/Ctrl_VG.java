package com.afa1997.electrournapi.ctrl;

import com.afa1997.electrournapi.mod.*;
import com.afa1997.electrournapi.repos.*;
import com.afa1997.electrournapi.utils.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @RequestMapping(value = "/voting/end_turn", method = RequestMethod.GET)
    public ResponseEntity<ArrayNode> finalizeVotingTurn(){
        // Get list of voted candidates.
        List<VoteReg> vt_rslt = repo_votereg.countTop1ByCandidate_to();
        ArrayList<String> roles_with_winner = new ArrayList<>();

        // Gets both turns information, used to shift through turns depending of roles with a winner.
        Turn turn_1_data = repo_t.findById(1).orElse(null);
        Turn turn_2_data = repo_t.findById(2).orElse(null);

        boolean presi_chosen = false, gov_chosen = false, senator_chosen = false, fedrep_chosen = false, intstrep_chosen = false;
        int voting_act = 0;

        // Check if there's winners for each of these roles, per the specification of the assignment: senator, federal representative and intrastate representative. Also, if there's no chosen president and/or governor, start second round.
        for(int x = 0 ; x < vt_rslt.size() ; x++) {
            if(vt_rslt.get(x).getChosen_role().getId_ct() == 1)
                presi_chosen = true;
            else if(vt_rslt.get(x).getChosen_role().getId_ct() == 2)
                gov_chosen = true;
            else if(vt_rslt.get(x).getChosen_role().getId_ct() == 3)
                senator_chosen = true;
            else if(vt_rslt.get(x).getChosen_role().getId_ct() == 4)
                fedrep_chosen = true;
            else if(vt_rslt.get(x).getChosen_role().getId_ct() == 5)
                intstrep_chosen = true;
        }

        // In order to begin the second round, either/both president and governor MUST NOT have been chosen, additionaly, the other three roles MUST have a winner.
        if(senator_chosen && fedrep_chosen && intstrep_chosen) {
            // If both president and governor were chosen, tell the user there's no need for a second round of votes, which won't begin.
            if(presi_chosen && gov_chosen && turn_1_data.isIs_active()) {
                voting_act = 3;

                // Voting period ends here.
                turn_1_data.setDt_end(LocalDate.now().toString());
                turn_1_data.setIs_active(false);
                repo_t.save(turn_1_data);
            }
            // Same as above but in the case president and/or governor needed to be chosen on turn 2.
            else if(presi_chosen && gov_chosen && turn_2_data.isIs_active()) {
                voting_act = 2;

                // Voting period ends here.
                turn_2_data.setDt_end(LocalDate.now().toString());
                turn_2_data.setIs_active(false);
                repo_t.save(turn_2_data);
            }
            // Otherwise, if either/both president and governor winners are missing, tell this to the user and start second round.
            else {
                voting_act = 1;

                // Shift to second turn.
                turn_1_data.setDt_end(LocalDate.now().toString());
                turn_1_data.setIs_active(false);
                turn_2_data.setDt_begin(LocalDate.now().toString());
                turn_2_data.setIs_active(true);

                // Save active turn state.
                turn_1_data.setId_turn(1);
                repo_t.save(turn_1_data);
                turn_2_data.setId_turn(2);
                repo_t.save(turn_2_data);
            }
        }

        // Used to tell the user in the response which roles don't have a winner (if any) and if a second round of voting begins.
        if(!presi_chosen)
            roles_with_winner.add("president");
        if(!gov_chosen)
            roles_with_winner.add("governor");
        if(!senator_chosen)
            roles_with_winner.add("senator");
        if(!fedrep_chosen)
            roles_with_winner.add("federal representative");
        if(!intstrep_chosen)
            roles_with_winner.add("intrastate representative");

        // Build the JSON and put it in the body of the response, the body shows the winning candidates for each role.
        return ResponseEntity.ok().body(buildResponseWinnersVG(vt_rslt, voting_act, roles_with_winner));
    }

    // Prepares response for getting most voted candidates by role.
    @Autowired
    ObjectMapper om_vg;
    public ArrayNode buildResponseWinnersVG(List<VoteReg> in_lvg, int vg_st, ArrayList<String> missing_roles){
        int votes_count = 0;

        ArrayNode an_vg_ranks = om_vg.createArrayNode();

        for(int x = 0 ; x < in_lvg.size() ; x++) {
            ObjectNode objn_vg = om_vg.createObjectNode();

            objn_vg.put("candidate_name", in_lvg.get(x).getVoted_candidate().getFirst_name() + " " + in_lvg.get(x).getVoted_candidate().getLast_name());
            objn_vg.put("elected_to", in_lvg.get(x).getChosen_role().getName());
            objn_vg.put("votes_count", repo_votereg.listCountOfVotesPerCto().get(x));

            an_vg_ranks.add(objn_vg);
        }

        ObjectNode voting_can_end = om_vg.createObjectNode();

        StringBuilder voting_end_msg = new StringBuilder();

        if(vg_st == 0)
            voting_end_msg.append("The following role(s) don't have winners yet: ");
        else if(vg_st == 1)
            voting_end_msg.append("Senator and both representative types have winners, but the following role(s) must be voted on the second round that just began: ");
        else if(vg_st == 2)
            voting_end_msg.append("Turn 2 finished with all roles having winners");
        else if(vg_st == 3)
            voting_end_msg.append("All roles have a winner, there's no need to start a second turn");

        if(missing_roles.size() > 0 && vg_st < 2) {
            for (int h = 0; h < missing_roles.size(); h++) {
                voting_end_msg.append(missing_roles.get(h));

                // This and the appended dot below are for message aesthetic.
                if (h < missing_roles.size() - 2)
                    voting_end_msg.append(", ");
                else if (h == missing_roles.size() - 2)
                    voting_end_msg.append(" and ");
            }
        }

        voting_end_msg.append(".");

        voting_can_end.put("did_voting_end", voting_end_msg.toString());

        an_vg_ranks.add(voting_can_end);

        return an_vg_ranks;
    }
}