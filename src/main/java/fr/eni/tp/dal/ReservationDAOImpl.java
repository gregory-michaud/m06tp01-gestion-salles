package fr.eni.tp.dal;

import fr.eni.tp.bo.Reservation;
import fr.eni.tp.bo.Salle;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationDAOImpl implements ReservationDAO {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String INSERT = "INSERT INTO reservations(date, heure_debut, heure_fin, duree, nom_employe, nom_salle ) VALUES (:date, :heureDebut, :heureFin, :duree, :nomEmploye, :nomSalle);";
    private final String FIND_ALL = "SELECT id, date, heure_debut, heure_fin, duree, nom_employe, nom_salle FROM reservations;";
    private final String COUNT_EXIST = "SELECT COUNT(*) FROM reservations WHERE nom_salle = :nomSalle AND date = :date AND heure_fin > CAST(:heureDebut AS TIME)  AND heure_debut < CAST(:heureFin AS TIME) ;";

    public ReservationDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Reservation reservation) {
        // La clef primaire est auto-générée par la base - il faut donc la récupérer
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("date", reservation.getDate());
        params.addValue("heureDebut", reservation.getHeureDebut());
        params.addValue("heureFin", reservation.getHeureFin());
        params.addValue("duree", reservation.getDuree());
        params.addValue("nomEmploye", reservation.getNomEmploye());
        params.addValue("nomSalle", reservation.getSalle().getNom());
        jdbcTemplate.update(INSERT, params, keyHolder);

        if (keyHolder != null && keyHolder.getKey() != null) {
            // Mise à jour de l'identifiant auto-généré par la base
            reservation.setId(keyHolder.getKey().longValue());
        }
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(FIND_ALL, new ReservationRowMapper());
    }

    @Override
    public boolean existsBySalleAndCreneau(String nomSalle, LocalDate date, LocalTime debut, LocalTime fin) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nomSalle", nomSalle);
        params.addValue("date", date);
        params.addValue("heureDebut", debut);
        params.addValue("heureFin", fin);
        Integer count = jdbcTemplate.queryForObject(COUNT_EXIST, params, Integer.class);
        return count != null && count > 0;
    }

    class ReservationRowMapper implements RowMapper<Reservation> {
        @Override
        public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
            Reservation reservation = new Reservation();
            reservation.setId(rs.getLong("id"));
            reservation.setDate(rs.getDate("date").toLocalDate());
            reservation.setHeureDebut(rs.getTime("heure_debut").toLocalTime());
            reservation.setHeureFin(rs.getTime("heure_fin").toLocalTime());
            reservation.setDuree(rs.getInt("duree"));
            reservation.setNomEmploye(rs.getString("nom_employe"));

            Salle salle = new Salle();
            salle.setNom(rs.getString("nom_salle"));
            reservation.setSalle(salle);
            return reservation;
        }
    }
}
