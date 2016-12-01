package ua.epam.spring.hometask.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Wojciech_Soltys on 30.11.2016.
 */
public class EventDaoImpl implements EventDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        EventRowCallbackHandler eventRowCallbackHandler = new EventRowCallbackHandler();
        final String SELECT_SQL = "select events.*, eventAirDates.*, auditorium.*, vipSeats.* " +
                "from events " +
                "left outer join eventAirDates on events.id = eventAirDates.eventId " +
                "inner join auditorium on eventAirDates.auditoriumId = auditorium.id " +
                "left outer join vipSeats on auditorium.id = vipSeats.auditoriumId " +
                "where events.name = ? "+
                "order by events.id, auditorium.id";
        jdbcTemplate.query(
                SELECT_SQL,
                eventRowCallbackHandler,
                name
        );
        return eventRowCallbackHandler.getFirst();
    }

    @Override
    public Event save(@Nonnull Event object) {
        final String INSERT_SQL_1 = "INSERT INTO events (name, basePrice, rating) VALUES (?,?,?)";
        final String INSERT_SQL_2 = "INSERT INTO eventAirDates (eventId, airDate, auditoriumId) VALUES (?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_SQL_1, new String[] {"id"});
                    ps.setString(1, object.getName());
                    ps.setDouble(2, object.getBasePrice());
                    ps.setString(3,object.getRating().name());
                    return ps;
                },
                keyHolder);
        object.setId(keyHolder.getKey().longValue());

        object.getAuditoriums().forEach((k,v) -> {
            jdbcTemplate.update(INSERT_SQL_2, new Object[] {object.getId(), Timestamp.valueOf(k), v.getId()});
        });

        return object;
    }

    @Override
    public void remove(@Nonnull Event object) {
        final String DELETE_SQL_1 = "DELETE FROM eventAirDates WHERE eventId = ?";
        final String DELETE_SQL_2 = "DELETE FROM events WHERE id = ?";

        jdbcTemplate.update(DELETE_SQL_1, new Object[]{object.getId()});
        jdbcTemplate.update(DELETE_SQL_2, new Object[]{object.getId()});
    }

    @Override
    public Event getById(@Nonnull Long id) {
        EventRowCallbackHandler eventRowCallbackHandler = new EventRowCallbackHandler();
        final String SELECT_SQL = "select events.*, eventAirDates.*, auditorium.*, vipSeats.* " +
                "from events " +
                "left outer join eventAirDates on events.id = eventAirDates.eventId " +
                "inner join auditorium on eventAirDates.auditoriumId = auditorium.id " +
                "left outer join vipSeats on auditorium.id = vipSeats.auditoriumId " +
                "where events.id = ? "+
                "order by events.id, auditorium.id";
        jdbcTemplate.query(
                SELECT_SQL,
                eventRowCallbackHandler,
                id
        );
        return eventRowCallbackHandler.getFirst();
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {

        EventRowCallbackHandler eventRowCallbackHandler = new EventRowCallbackHandler();
        final String SELECT_SQL = "select events.*, eventAirDates.*, auditorium.*, vipSeats.* " +
                "from events " +
                "left outer join eventAirDates on events.id = eventAirDates.eventId " +
                "inner join auditorium on eventAirDates.auditoriumId = auditorium.id " +
                "left outer join vipSeats on auditorium.id = vipSeats.auditoriumId " +
                "order by events.id, auditorium.id";
        jdbcTemplate.query(
                SELECT_SQL,
                eventRowCallbackHandler
        );
        return eventRowCallbackHandler.getAll();
    }
}
