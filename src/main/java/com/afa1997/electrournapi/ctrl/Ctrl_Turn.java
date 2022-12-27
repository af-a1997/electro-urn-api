package com.afa1997.electrournapi.ctrl;

import com.afa1997.electrournapi.mod.Turn;
import com.afa1997.electrournapi.repos.Repo_Turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

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

    // Shifts through voting turn cycle generated upon the initial start-up, there's three possible states, advancing per request to </voting/shift>, these states are described on [utils.GetActiveTurn]. To shift the through the cycle WHILE making the calculations, send an empty request to </voting/end_turn>.
    @RequestMapping(value = "/voting/shift", method = RequestMethod.GET)
    public ResponseEntity<Turn> shiftActTurn(){
        Turn save_turn_changes_1 = repo_t.findById(1).orElse(null);
        Turn save_turn_changes_2 = repo_t.findById(2).orElse(null);

        int turn_no = 0;
        if(save_turn_changes_1.isIs_active())
            turn_no = 1;
        else if(save_turn_changes_2.isIs_active())
            turn_no = 2;

        // TODO: may want to remove, as it's used for testing if the turn number is coming through.
        System.out.println("Active turn = " + turn_no);

        if(turn_no == 0){
            save_turn_changes_1.setDt_begin(LocalDate.now().toString());
            save_turn_changes_1.setDt_end(null);
            save_turn_changes_1.setIs_active(true);

            save_turn_changes_2.setDt_begin(null);
            save_turn_changes_2.setDt_end(null);
        }
        else if(turn_no == 1) {
            save_turn_changes_1.setDt_end(LocalDate.now().toString());
            save_turn_changes_2.setDt_begin(LocalDate.now().toString());

            save_turn_changes_1.setIs_active(false);
            save_turn_changes_2.setIs_active(true);
        }
        else if(turn_no == 2){
            save_turn_changes_2.setDt_end(LocalDate.now().toString());
            save_turn_changes_2.setIs_active(false);
        }

        // Update voting turns 1 and 2 states as needed.

        save_turn_changes_1.setId_turn(1);
        repo_t.save(save_turn_changes_1);

        save_turn_changes_2.setId_turn(2);
        repo_t.save(save_turn_changes_2);

        // Response to tell the user the shift was done.
        return ResponseEntity.ok().build();
    }
}