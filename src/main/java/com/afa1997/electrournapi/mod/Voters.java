package com.afa1997.electrournapi.mod;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

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
}
