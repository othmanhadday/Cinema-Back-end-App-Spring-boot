package com.oth.cinemaappl.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ticket implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double prix;
    private String nomclient;
    @Column(unique = false,nullable = true)
    private Integer codePayment;
    private boolean reservee;
    @ManyToOne
    private ProjectionFilm projectionFilm;
    @ManyToOne
    private Place place;
}
