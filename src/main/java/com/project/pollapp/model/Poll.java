package com.project.pollapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "Polls")
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String pollName;
   @JsonManagedReference
    @OneToMany(mappedBy = "poll",cascade=CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Votes> votes = new ArrayList<>();


}
