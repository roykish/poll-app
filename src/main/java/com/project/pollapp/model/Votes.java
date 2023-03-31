package com.project.pollapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "Votes")
public class Votes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String voterName;
    private String voterVote;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "POLL_ID")
    private Poll poll;

}
