package fr.eni.tp.bll;

import fr.eni.tp.bo.Reservation;
import fr.eni.tp.bo.Salle;
import fr.eni.tp.dal.ReservationDAO;
import fr.eni.tp.dal.SalleDAO;
import fr.eni.tp.exceptions.BusinessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {


    private Log logger = LogFactory.getLog(getClass());

    private final SalleDAO salleDAO;
    private final ReservationDAO reservationDAO;

    public ReservationServiceImpl(SalleDAO salleDAO, ReservationDAO reservationDAO) {
        this.salleDAO = salleDAO;
        this.reservationDAO = reservationDAO;
    }

    @Override
    public List<Reservation> getReservations() {
        try {
            List<Reservation> resa = reservationDAO.findAll();
            resa.forEach(r -> {
                // Association entre Reservation-Salle
                Salle s = getSalle(r.getSalle().getNom());
                if (s == null) {
                    BusinessException be = new BusinessException();
                    be.add("Erreur technique lors de la récupération d'une salle");
                    throw be;
                }
                r.setSalle(s);
            });
            return resa;
        } catch (DataAccessException e) {
            logger.error(e);
            BusinessException be = new BusinessException();
            be.add("Erreur technique lors récupération des réservations.");
            throw be;
        }
    }

    /**
     *
     * @param nom
     * @return Retourner une salle depuis son ou null si aucune trouvée
     */
    private Salle getSalle(String nom) {
        try{
            return salleDAO.findByNom(nom).orElse(null);
        } catch (DataAccessException e) {
            logger.error(e);
            BusinessException be = new BusinessException();
            be.add("Erreur technique lors de la récupération d'une salle par son nom'.");
            throw be;
        }
    }

    @Override
    public List<Salle> getSalles() {
        try {
            return salleDAO.findAll();
        } catch (DataAccessException e) {
            logger.error(e);
            BusinessException be = new BusinessException();
            be.add("Erreur technique lors de la récupération des salles.");
            throw be;
        }
    }


    @Override
    public void creerReservation(Reservation reservation) {
        // Validaion des données
        BusinessException be = new BusinessException();
        try {
            boolean isValid = true;
            isValid &= verifDateEtHeures(reservation, be);
            isValid &= salleExiste(reservation.getSalle().getNom(), be);
            isValid &= verifCreneauLibre(reservation, be);

            // Propagée BusinessException si échec des validations métiers
            if (!isValid) {
                throw be;
            }

            // Calcul métier : durée
            long dureeMinutes = Duration.between(
                            reservation.getHeureDebut(),
                            reservation.getHeureFin())
                    .toMinutes();
            reservation.setDuree((int) dureeMinutes);

            logger.debug("Nouvelle réservation - " + reservation);
            reservationDAO.insert(reservation);
        } catch (DataAccessException ex) {
            logger.error(ex);
            be.add("Erreur technique lors de l’enregistrement de la réservation.");
            throw be;
        }

    }

    private boolean verifDateEtHeures(Reservation reservation, BusinessException be) {
        boolean isOk = reservation.getHeureDebut().isBefore(reservation.getHeureFin());
        if (!isOk) {
            be.add("L'heure de fin doit être supérieure à l'heure de début");
        }
        return isOk;
    }

    private boolean salleExiste(String nomSalle, BusinessException be) {
        Salle salleDB = getSalle(nomSalle);
        if (salleDB == null) {
            be.add("La salle n'existe pas.");
            return false;
        }
        return true;
    }

    private boolean verifCreneauLibre(Reservation resa, BusinessException be) {
        boolean ok = reservationDAO.existsBySalleAndCreneau(resa.getSalle().getNom(), resa.getDate(), resa.getHeureDebut(), resa.getHeureFin());
        if (ok) {
            be.add("Le créneau est déjà occupé !");
        }
        return !ok;
    }


}
