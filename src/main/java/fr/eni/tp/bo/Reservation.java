package fr.eni.tp.bo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private Long id;

    @NotBlank
    @Size(max = 250)
    private String nomEmploye;

    @NotNull
    @Future
    private LocalDate date;

    @NotNull
    private LocalTime heureDebut;

    @NotNull
    private LocalTime heureFin;

    private long duree;

    @NotNull
    @Valid
    private Salle salle;


    public Reservation() {
    }

    public Reservation(String nomEmploye, LocalDate date, LocalTime heureDebut, LocalTime heureFin, long duree, Salle salle) {
        this.nomEmploye = nomEmploye;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.duree = duree;
        this.salle = salle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEmploye() {
        return nomEmploye;
    }

    public void setNomEmploye(String nomEmploye) {
        this.nomEmploye = nomEmploye;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public long getDuree() {
        return duree;
    }

    public void setDuree(long duree) {
        this.duree = duree;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(nomEmploye, that.nomEmploye) && Objects.equals(date, that.date) && Objects.equals(heureDebut, that.heureDebut) && Objects.equals(heureFin, that.heureFin) && Objects.equals(duree, that.duree) && Objects.equals(salle, that.salle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomEmploye, date, heureDebut, heureFin, salle);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", nomEmploye='" + nomEmploye + '\'' +
                ", date=" + date +
                ", heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                ", duree=" + duree +
                ", salle=" + salle +
                '}';
    }
}
