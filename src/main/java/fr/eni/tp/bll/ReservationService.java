package fr.eni.tp.bll;

import fr.eni.tp.bo.Reservation;
import fr.eni.tp.bo.Salle;

import java.util.List;

public interface ReservationService {
    List<Reservation> getReservations();

    List<Salle> getSalles();

    void creerReservation(Reservation reservation);
}
