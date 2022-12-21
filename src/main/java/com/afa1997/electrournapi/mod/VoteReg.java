package com.afa1997.electrournapi.mod;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "vote_reg")
public class VoteReg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_vote;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String dt_vote;

    private int turn_id;

    // Foreign keys here:

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "voter_id", referencedColumnName = "id_voter")
    private Voters cndt_voter;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_to", referencedColumnName = "id_ct")
    private CandidateTypes chosen_role;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vt_cndt_id", referencedColumnName = "candidate_id")
    private Candidates voted_candidate;

    // Getters and setters:

    public int getId_vote() {
        return id_vote;
    }

    public void setId_vote(int id_vote) {
        this.id_vote = id_vote;
    }

    public String getDt_vote() {
        return dt_vote;
    }

    public void setDt_vote(String dt_vote) {
        this.dt_vote = dt_vote;
    }

    public Candidates getVoted_candidate() {
        return voted_candidate;
    }

    // For foreign keys:

    public Voters getCndt_voter() {
        return cndt_voter;
    }

    public void setCndt_voter(Voters cndt_voter) {
        this.cndt_voter = cndt_voter;
    }

    public void setVoted_candidate(Candidates voted_candidate) {
        this.voted_candidate = voted_candidate;
    }

    public int getTurn_id() {
        return turn_id;
    }

    public void setTurn_id(int turn_id) {
        this.turn_id = turn_id;
    }

    public CandidateTypes getChosen_role() {
        return chosen_role;
    }

    public void setChosen_role(CandidateTypes chosen_role) {
        this.chosen_role = chosen_role;
    }
}