package fr.eni.tp.bo;

import java.util.Objects;

public class Salle {
    private String nom;

    private int capacite;

    public Salle() {
    }

    public Salle(String nom, int capacite) {
        this.nom = nom;
        this.capacite = capacite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Salle salle = (Salle) o;
        return capacite == salle.capacite  && Objects.equals(nom, salle.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash( nom, capacite);
    }

    @Override
    public String toString() {
        return "Salle{" +
                "nom='" + nom + '\'' +
                ", capacite=" + capacite +
                '}';
    }
}
