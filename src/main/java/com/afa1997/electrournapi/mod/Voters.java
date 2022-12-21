package com.afa1997.electrournapi.mod;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "voters")
public class Voters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_voter;

    @Column(columnDefinition = "TINYTEXT")
    private String first_name;

    @Column(columnDefinition = "TINYTEXT")
    private String last_name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String date_birth;

    @JsonIgnore
    @OneToMany(mappedBy = "cndt_voter")
    private Set<VoteReg> cndt_voter_ref = new HashSet<>();

    // Getters and setters:

    public int getId_voter() {
        return id_voter;
    }

    public void setId_voter(int id_voter) {
        this.id_voter = id_voter;
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

    // For foreign key:

    public Set<VoteReg> getCndt_voter_ref() {
        return cndt_voter_ref;
    }

    public void setCndt_voter_ref(Set<VoteReg> cndt_voter_ref) {
        this.cndt_voter_ref = cndt_voter_ref;
    }
}
