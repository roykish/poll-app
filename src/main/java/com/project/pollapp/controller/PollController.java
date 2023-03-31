package com.project.pollapp.controller;

import com.project.pollapp.model.Poll;
import com.project.pollapp.model.Votes;
import com.project.pollapp.repository.PollRepository;
import com.project.pollapp.repository.VotesRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PollController {
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private VotesRepository votesRepository;


    @PostMapping("/addPoll")
    private ResponseEntity<Poll> addPoll(@RequestBody Poll poll){
        Poll pollObject = pollRepository.save(poll);
        return new ResponseEntity<>(pollObject,HttpStatus.OK);
    }

    @GetMapping("/getAllPolls")
    private ResponseEntity<List<Poll>> getAllPolls(){
        try{
            List<Poll> pollList = pollRepository.findAll();
            return new ResponseEntity<>(pollList,HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/selectedPoll/{pollId}")
    private ResponseEntity<Poll> findPollNameById(@PathVariable("pollId") Long pollId){
        Poll poll= pollRepository.findById(pollId).get();
        return new ResponseEntity<>(poll, HttpStatus.OK);
    }

   @SneakyThrows
    @PostMapping("/addVote/{pollId}/vote")
    public ResponseEntity<Votes> createComment(@PathVariable(value = "pollId") Long pollId, @RequestBody Votes voteRequest) {
       Poll poll = pollRepository.getById(pollId);
       if(poll != null){
           Votes votes = new Votes();
           int count = votesRepository.getByVoterNameAndPollId(voteRequest.getVoterName(),pollId);
           if(count == 0){
               votes.setPoll(poll);
               votes.setVoterName(voteRequest.getVoterName());
               votes.setVoterVote(voteRequest.getVoterVote());
               Votes saved = votesRepository.save(votes);
               return new ResponseEntity<>(votes, HttpStatus.OK);
           }else{
               return new ResponseEntity<>(votes, HttpStatus.TOO_MANY_REQUESTS);
           }
       }
       else {
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
    }
}
