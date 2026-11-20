package fr.eni.tp.dal;

import fr.eni.tp.bo.Salle;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SalleDAOImpl implements SalleDAO {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String INSERT = "INSERT INTO salles(nom, capacite) VALUES (:nom, :capacite);";
    private final String FIND_BY_NOM = "SELECT nom, capacite FROM salles WHERE nom = :nom;";
    private final String FIND_ALL = "SELECT nom, capacite FROM salles;";

    public SalleDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Salle salle) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nom", salle.getNom());
        params.addValue("capacite", salle.getCapacite());
        jdbcTemplate.update(INSERT, params);
    }

    @Override
    public Optional<Salle> findByNom(String nom) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("nom", nom);
        try {
            Salle salle = jdbcTemplate.queryForObject(
                    FIND_BY_NOM,
                    params,
                    new BeanPropertyRowMapper<>(Salle.class)
            );
            return Optional.ofNullable(salle);
        } catch (EmptyResultDataAccessException e) {
            // Aucune salle trouvée - cas acceptable et retour d'un empty
            return Optional.empty();
        }
        // nom est unique
        // Pas besoin de catch IncorrectResultSizeDataAccessException
        // si ça arrive, c'est un bug de données => mieux vaut laisser remonter l'exception vers la BLL
    }

    @Override
    public List<Salle> findAll() {
        return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Salle.class));
    }
}
