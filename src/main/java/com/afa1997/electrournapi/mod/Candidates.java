package com.afa1997.electrournapi.mod;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "candidates")
public class Candidates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int candidate_id;

    @Column(columnDefinition = "TINYTEXT")
    private String first_name;

    @Column(columnDefinition = "TINYTEXT")
    private String last_name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(columnDefinition = "DATE")
    private String date_birth;

    @JsonIgnore
    @OneToMany(mappedBy = "voted_candidate")
    private Set<VoteReg> voter_votes_ref = new HashSet<>();

    // Getters and setters:

    public int getCandidate_id() {
        return candidate_id;
    }

    public void setCandidate_id(int candidate_id) {
        this.candidate_id = candidate_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(String date_birth) {
        this.date_birth = date_birth;
    }

    public Set<VoteReg> getVoter_votes_ref() {
        return voter_votes_ref;
    }

    public void setVoter_votes_ref(Set<VoteReg> voter_votes_ref) {
        this.voter_votes_ref = voter_votes_ref;
    }
}