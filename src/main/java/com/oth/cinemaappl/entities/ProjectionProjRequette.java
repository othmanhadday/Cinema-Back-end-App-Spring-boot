package com.oth.cinemaappl.entities;

import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(name = "p1",types = {ProjectionFilm.class})
public interface ProjectionProjRequette {
    public Long getId();
    public double getPrix();
    public Date getDate();
    public Salle getSalle();
    public Film getFilm();
    public Seance getSeance();
    public List<Ticket> getTickets();
}
