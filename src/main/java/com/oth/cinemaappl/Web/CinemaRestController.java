package com.oth.cinemaappl.Web;

import com.oth.cinemaappl.Dao.FilmRepository;
import com.oth.cinemaappl.Dao.TicketRepository;
import com.oth.cinemaappl.entities.Film;
import com.oth.cinemaappl.entities.Ticket;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping(path = "/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable(name = "id")Long id) throws IOException {
        Film film =filmRepository.findById(id).get();
        String photoName = film.getPhoto();
        File file = new File(System.getProperty("user.home")+"/cinema/images/"+photoName);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    @PostMapping("/payerTickets")
    @Transactional
    public List<Ticket> payerTickers(@RequestBody TicketForm ticketForm){
        List<Ticket> listTickets = new ArrayList<Ticket>();
        ticketForm.getTickets().forEach(idTcket->{
            Ticket ticket = ticketRepository.findById(idTcket).get();
            ticket.setNomclient(ticketForm.getNomClient());
            ticket.setCodePayment(ticketForm.getCodePayment());
            ticket.setReservee(true);
            ticketRepository.save(ticket);
            listTickets.add(ticket);
        });
        return listTickets;
    }
}
@Data
class TicketForm{
    private String nomClient;
    private int codePayment;
    private List<Long> tickets=new ArrayList<Long>();
}
