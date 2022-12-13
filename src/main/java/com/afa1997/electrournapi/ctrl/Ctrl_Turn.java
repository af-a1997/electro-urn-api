package com.afa1997.electrournapi.ctrl;

import com.afa1997.electrournapi.mod.Turn;
import com.afa1997.electrournapi.repos.Repo_Turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Controller
public class Ctrl_Turn {
    @Autowired
    Repo_Turn repo_t;

    @RequestMapping(value = "/turn/{id_turn}", method = RequestMethod.GET)
    public ResponseEntity<Turn> getTurnData(@PathVariable("id_turn") int param_id_turn){
        Optional<Turn> turn_periods = repo_t.findById(param_id_turn);

        if(turn_periods.isPresent())
            return ResponseEntity.ok().body(turn_periods.get());
        else
            return ResponseEntity.notFound().build();
    }

    // Changes active voting turn by cycling through three possible states, advancing per request to </end_turn>. There's always two turns created at start from [data.sql] and these are being used per the assignment's specification.
    @RequestMapping(value = "/end_turn", method = RequestMethod.GET)
    public ResponseEntity<Turn> endTurn(){
        List<Turn> list_of_turns = repo_t.findAll();
        Turn save_turn_changes_1 = repo_t.findById(1).orElse(null);
        Turn save_turn_changes_2 = repo_t.findById(2).orElse(null);

        // If no voting turn is active, enable first turn.
        if(!list_of_turns.get(0).isIs_active() && !list_of_turns.get(1).isIs_active()){
            System.out.println("Turn 1 is on");

            save_turn_changes_1.setDt_begin(LocalDate.now().toString());
            save_turn_changes_1.setDt_end(null);
            save_turn_changes_1.setIs_active(true);

            save_turn_changes_2.setDt_begin(null);
            save_turn_changes_2.setDt_end(null);
        }
        // If turn 1 is active, end it and activate turn 2.
        else if(list_of_turns.get(0).isIs_active() && !list_of_turns.get(1).isIs_active()) {
            System.out.println("Turn 1 is off, and turn 2 is on");

            save_turn_changes_1.setDt_end(LocalDate.now().toString());
            save_turn_changes_2.setDt_begin(LocalDate.now().toString());

            save_turn_changes_1.setIs_active(false);
            save_turn_changes_2.setIs_active(true);
        }
        // If turn 2 is active, end it.
        else if(list_of_turns.get(1).isIs_active() && !list_of_turns.get(0).isIs_active()){
            System.out.println("Turn 2 is off");

            save_turn_changes_2.setDt_end(LocalDate.now().toString());
            save_turn_changes_2.setIs_active(false);
        }

        // Register changes to voting turn 1 and 2.

        save_turn_changes_1.setId_turn(1);
        repo_t.save(save_turn_changes_1);

        save_turn_changes_2.setId_turn(2);
        repo_t.save(save_turn_changes_2);

        // Just a 202 status response.
        return ResponseEntity.accepted().build();
    }
}