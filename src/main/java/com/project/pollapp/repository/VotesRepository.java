package com.project.pollapp.repository;

import com.project.pollapp.model.Votes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VotesRepository extends JpaRepository<Votes,Long> {

    @Query(value = "SELECT count(*) FROM VOTES v where v.VOTER_NAME = ?1 AND v.POLL_ID = ?2", nativeQuery = true)
    Integer getByVoterNameAndPollId(String name, Long pollId);
}
