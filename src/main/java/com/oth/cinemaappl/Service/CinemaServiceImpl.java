package com.oth.cinemaappl.Service;

import com.oth.cinemaappl.Dao.*;
import com.oth.cinemaappl.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaServiceImpl implements ICinemaService {
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void initVilles() {
        Stream.of("Casablanca", "Marrakech", "Rabat", "Agadir", "Tanger").forEach(v -> {
            Ville ville = new Ville();
            ville.setName(v);
            villeRepository.save(ville);
        });
    }

    @Override
    public void initCinemas() {

        villeRepository.findAll().forEach(ville -> {
            Stream.of("MegaRama", "IMAX", "FOUNOUN", "CHahrazad", "Daoulix")
                    .forEach(c->{
                        Cinema cinema = new Cinema();
                        cinema.setName(c);
                        cinema.setNombreSalles(3 + (int) (Math.random() * 7));
                        cinema.setVille(ville);
                        cinemaRepository.save(cinema);
                    });
                });
    }

    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(cinema -> {
            for (int i = 0; i < cinema.getNombreSalles(); i++) {
                Salle salle = new Salle();
                salle.setName("Salle " + (i + 1));
                salle.setCinema(cinema);
                salle.setNombrePlace(15 + (int) (Math.random() * 20));
                salleRepository.save(salle);
            }
        });
    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle -> {
            for (int i = 0; i < salle.getNombrePlace(); i++) {
                Place place = new Place();
                place.setNumero(i + 1);
                place.setSalle(salle);
                placeRepository.save(place);
            }
        });
    }

    @Override
    public void initSeance() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Stream.of("12:00", "15:00", "17:00", "19:00", "21:00").forEach(s -> {
            Seance seance = new Seance();
            try {
                seance.setHeureDebut(dateFormat.parse(s));
                seanceRepository.save(seance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initCategories() {
        Stream.of("Hstoire", "Actions", "Fiction", "Drama").forEach(s -> {
            Categorie categorie = new Categorie();
            categorie.setName(s);
            categorieRepository.save(categorie);
        });
    }

    @Override
    public void initFilms() {
        double[] durees = new double[]{1, 1.5, 2, 2.5, 3};
        List<Categorie> ca = categorieRepository.findAll();
        Stream.of("Game of thrones", "spiderman", "IronMan", "HitMan",
                "Cat Women", "No Time to Die", "After we Collided", "F9")
                .forEach(f -> {
                    Film film = new Film();
                    film.setTitre(f);
                    film.setDuree(durees[new Random().nextInt(durees.length)]);
                    film.setPhoto(f + ".jpg");
                    film.setCategorie(ca.get(new Random().nextInt(ca.size())));
                    filmRepository.save(film);
                });
    }

    @Override
    public void initProjections() {
        double[] prices = new double[]{30, 50, 60, 70, 90, 100};
        List<Film> films = filmRepository.findAll();
        villeRepository.findAll().forEach(ville -> {
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                    int index = new Random().nextInt(films.size());
                    Film film = films.get(index);
                    seanceRepository.findAll().forEach(seance -> {
                        ProjectionFilm projectionFilm = new ProjectionFilm();
                        projectionFilm.setDate(new Date());
                        projectionFilm.setFilm(film);
                        projectionFilm.setPrix(prices[new Random().nextInt(prices.length)]);
                        projectionFilm.setSalle(salle);
                        projectionFilm.setSeance(seance);
                        projectionRepository.save(projectionFilm);
                    });
                });
            });
        });
    }

    @Override
    public void initTickets() {
        projectionRepository.findAll().forEach(p -> {
            p.getSalle().getPlaces().forEach(place -> {
                Ticket ticket = new Ticket();
                ticket.setPlace(place);
                ticket.setPrix(p.getPrix());
                ticket.setProjectionFilm(p);
                ticket.setReservee(false);
                ticketRepository.save(ticket);
            });
        });
    }
}
