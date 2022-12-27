package com.afa1997.electrournapi.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afa1997.electrournapi.mod.VoteReg;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Repo_VG extends JpaRepository<VoteReg, Integer> {
    /*
     * Get most voted candidate to each specific role, there's up to 5 roles and blank votes.
     *
     * Query annotation defines the query to be made via the [value] string type parameter, and [nativeQuery] is a boolean type parameter that sets if you want to use SQL for the queries, setting to false or not passing this parameter means [value] is receiving JPQL instead.
     *
     * JPQL is largely similar to SQL but it has some glaring differences, learn more at: < https://en.wikibooks.org/wiki/Java_Persistence/JPQL >.
     */
    @Query(value = "SELECT * FROM vote_reg GROUP BY candidate_to;", nativeQuery = true)
    List<VoteReg> countTop1ByCandidate_to();

    // Additional method needed to get the amount of votes per role.
    @Query(value = "SELECT COUNT(candidate_to) AS ccto FROM vote_reg GROUP BY candidate_to;", nativeQuery = true)
    List<Integer> listCountOfVotesPerCto();

    // TODO: find out how to get [ccto]. Reading: < https://www.baeldung.com/spring-data-jpa-query >
}