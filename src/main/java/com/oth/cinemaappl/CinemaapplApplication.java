package com.oth.cinemaappl;

import com.oth.cinemaappl.Service.ICinemaService;
import com.oth.cinemaappl.entities.Categorie;
import com.oth.cinemaappl.entities.Film;
import com.oth.cinemaappl.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CinemaapplApplication implements CommandLineRunner {
    @Autowired
    private ICinemaService cinemaService;
    @Autowired
    private RepositoryRestConfiguration restConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(CinemaapplApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        restConfiguration.exposeIdsFor(Film.class, Categorie.class, Ticket.class);
        cinemaService.initVilles();
        cinemaService.initCinemas();
        cinemaService.initSalles();
        cinemaService.initPlaces();
        cinemaService.initSeance();
        cinemaService.initCategories();
        cinemaService.initFilms();
        cinemaService.initProjections();
        cinemaService.initTickets();
    }
}
