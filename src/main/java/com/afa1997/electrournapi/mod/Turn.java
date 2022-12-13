package com.afa1997.electrournapi.mod;

import jakarta.persistence.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "turn")
public class Turn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_turn;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(columnDefinition = "DATE")
    private String dt_begin;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(columnDefinition = "DATE")
    private String dt_end;

    @Value("false")
    private boolean is_active;

    // Getters and setters:

    public int getId_turn() {
        return id_turn;
    }

    public void setId_turn(int id_turn) {
        this.id_turn = id_turn;
    }

    public String getDt_begin() {
        return dt_begin;
    }

    public void setDt_begin(String dt_begin) {
        this.dt_begin = dt_begin;
    }

    public String getDt_end() {
        return dt_end;
    }

    public void setDt_end(String dt_end) {
        this.dt_end = dt_end;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}
