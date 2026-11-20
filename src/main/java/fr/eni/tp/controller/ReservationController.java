package fr.eni.tp.controller;

import fr.eni.tp.bll.ReservationService;
import fr.eni.tp.bo.Reservation;
import fr.eni.tp.bo.Salle;
import fr.eni.tp.exceptions.BusinessException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity getAllReservations() {
        try {
            List<Reservation> reservations = reservationService.getReservations();
            return ResponseEntity.ok(reservations);
        } catch (BusinessException be) {
            return ResponseEntity.badRequest().body(be.getMessage());
        }
    }

    @GetMapping("/salles")
    @ResponseBody
    public ResponseEntity getAllRooms() {
        try {
            List<Salle> salles = reservationService.getSalles();
            return ResponseEntity.ok(salles);
        } catch (BusinessException be) {
            return ResponseEntity.badRequest().body(be.getMessage());
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity creerReservation(@Valid Reservation reservation, BindingResult br) {
        System.out.println(reservation);
        if (br.hasErrors()) {
            String erreurs = br.getFieldErrors().stream()
                    .map(error -> error.getField() + " - " + error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            return ResponseEntity.badRequest().body("Erreurs :<br>" + erreurs);
        } else {
            try {
                reservationService.creerReservation(reservation);
                return ResponseEntity.ok(reservation);
            } catch (BusinessException be) {
                return ResponseEntity.badRequest().body(be.getMessage());
            }
        }
    }
}
