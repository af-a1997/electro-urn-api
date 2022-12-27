package com.afa1997.electrournapi.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afa1997.electrournapi.mod.VoteReg;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Repo_VG extends JpaRepository<VoteReg, Integer> {
    // SELECT *, COUNT(candidate_to) AS ccto FROM `vote_reg` GROUP BY candidate_to ORDER BY COUNT(candidate_to) DESC LIMIT 6;
    // @Query(value = "SELECT vg.*, COUNT(vg.candidate_to) AS ccto FROM VoteReg vg GROUP BY vg.candidate_to ORDER BY COUNT(vg.candidate_to) DESC LIMIT 6;")
    @Query(value = "SELECT *, COUNT(candidate_to) AS ccto FROM vote_reg WHERE candidate_to = :ct GROUP BY candidate_to;", nativeQuery = true)
    List<VoteReg> countTop1ByCandidate_to(@Param("ct") int p_id_ct);
    // Get most voted candidate to each specific role, there's up to 5 roles and blank votes.
}