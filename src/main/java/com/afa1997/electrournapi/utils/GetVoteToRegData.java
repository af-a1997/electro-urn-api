package com.afa1997.electrournapi.utils;

// This is an auxiliary class to get data from the JSON input when sending a vote.
public class GetVoteToRegData {
    private int id_ct;
    private int candidate_id;

    private int voter_id;

    // Getters and setters:

    public int getId_ct() {
        return id_ct;
    }

    public void setId_ct(int id_ct) {
        this.id_ct = id_ct;
    }

    public int getCandidate_id() {
        return candidate_id;
    }

    public void setCandidate_id(int candidate_id) {
        this.candidate_id = candidate_id;
    }

    public int getVoter_id() {
        return voter_id;
    }

    public void setVoter_id(int voter_id) {
        this.voter_id = voter_id;
    }
}
