package com.afa1997.electrournapi.mod;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "candidate_types")
public class CandidateTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_ct;

    @Column(columnDefinition = "TINYTEXT")
    private String name;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String notes;

    @JsonIgnore
    @OneToMany(mappedBy = "chosen_role")
    private Set<VoteReg> candidate_type_from_votereg = new HashSet<>();

    // Getters and setters

    public int getId_ct() {
        return id_ct;
    }

    public void setId_ct(int id_ct) {
        this.id_ct = id_ct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<VoteReg> getCandidate_type_from_votereg() {
        return candidate_type_from_votereg;
    }

    public void setCandidate_type_from_votereg(Set<VoteReg> candidate_type_from_votereg) {
        this.candidate_type_from_votereg = candidate_type_from_votereg;
    }
}
