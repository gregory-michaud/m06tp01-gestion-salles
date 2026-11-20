package fr.eni.tp.bll;

import fr.eni.tp.bo.Reservation;
import fr.eni.tp.bo.Salle;
import fr.eni.tp.dal.ReservationDAO;
import fr.eni.tp.dal.SalleDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final SalleDAO salleDAO;
    private final ReservationDAO reservationDAO;

    public ReservationServiceImpl(SalleDAO salleDAO, ReservationDAO reservationDAO) {
        this.salleDAO = salleDAO;
        this.reservationDAO = reservationDAO;
    }

    @Override
    public List<Reservation> getReservations() {
        return new ArrayList<>();// TODO
    }

    @Override
    public List<Salle> getSalles() {
        return new ArrayList<>();// TODO
    }

    @Override
    public void creerReservation(Reservation reservation) {
        // TODO
    }

}
