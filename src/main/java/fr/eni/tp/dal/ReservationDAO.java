package fr.eni.tp.dal;

import fr.eni.tp.bo.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationDAO {

    void insert(Reservation reservation);

    List<Reservation> findAll();

    boolean existsBySalleAndCreneau(String nomSalle, LocalDate date, LocalTime debut, LocalTime fin);
}
