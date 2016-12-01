package ua.epam.spring.hometask.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.epam.spring.hometask.domain.Auditorium;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Wojciech_Soltys on 28.11.2016.
 */
public class AuditoriumDaoImpl implements AuditoriumDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Auditorium save(@Nonnull Auditorium object) {
        final String INSERT_SQL_1 = "INSERT INTO auditorium (name,numberOfSeats) VALUES (?,?)";
        final String INSERT_SQL_2 = "INSERT INTO vipSeats (auditoriumId, numberOfSeat) VALUES (?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_SQL_1, new String[] {"id"});
                    ps.setString(1, object.getName());
                    ps.setLong(2, object.getNumberOfSeats());
                    return ps;
                },
                keyHolder);
        object.setId(keyHolder.getKey().longValue());

        for(Iterator it = object.getVipSeats().iterator(); it.hasNext();) {
            jdbcTemplate.update(
                    INSERT_SQL_2,
                    object.getId(),
                    it.next());
        }
        return object;
    }

    @Override
    public void remove(@Nonnull Auditorium object) {
        final String DELETE_SQL_1 = "DELETE FROM vipSeats WHERE auditoriumId = ?";
        final String DELETE_SQL_2 = "DELETE FROM auditorium WHERE id = ?";

        jdbcTemplate.update(DELETE_SQL_1, new Object[]{object.getId()});
        jdbcTemplate.update(DELETE_SQL_2, new Object[]{object.getId()});
    }

    @Override
    public Auditorium getById(@Nonnull Long id) {
        AuditoriumRowCallbackHandler auditoriumRowCallbackHandler = new AuditoriumRowCallbackHandler();
        final String SELECT_SQL = "select a.*, v.numberOfSeat from auditorium a " +
                "left outer join vipSeats v on a.id = v.auditoriumId " +
                "where a.id = ?" +
                "order by a.id";
        jdbcTemplate.query(
                SELECT_SQL,
                auditoriumRowCallbackHandler,
                id
        );
        return ((List<Auditorium>)auditoriumRowCallbackHandler.getAuditoriums()).get(0);
    }

    @Override
    public Set<Auditorium> getAll() {
        AuditoriumRowCallbackHandler auditoriumRowCallbackHandler = new AuditoriumRowCallbackHandler();
        final String SELECT_SQL = "select a.*, v.numberOfSeat from auditorium a " +
                "left outer join vipSeats v on a.id = v.auditoriumId " +
                "order by a.id";
        jdbcTemplate.query(
                SELECT_SQL,
                auditoriumRowCallbackHandler
        );
        return new HashSet<>(auditoriumRowCallbackHandler.getAuditoriums());
    }

    @Override
    public Auditorium getByName(@Nonnull String name) {
        AuditoriumRowCallbackHandler auditoriumRowCallbackHandler = new AuditoriumRowCallbackHandler();
        final String SELECT_SQL = "select a.*, v.numberOfSeat from auditorium a " +
                "left outer join vipSeats v on a.id = v.auditoriumId " +
                "where a.name = ?" +
                "order by a.id";
        jdbcTemplate.query(
                SELECT_SQL,
                auditoriumRowCallbackHandler,
                name
        );
        return ((List<Auditorium>)auditoriumRowCallbackHandler.getAuditoriums()).get(0);
    }
}
