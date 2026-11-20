package fr.eni.tp.dal;

import fr.eni.tp.bo.Salle;

import java.util.List;
import java.util.Optional;

public interface SalleDAO {
    void insert(Salle salle);

    Optional<Salle> findByNom(String nom);

    List<Salle> findAll();
}
