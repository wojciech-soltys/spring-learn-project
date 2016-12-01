package ua.epam.spring.hometask.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Collection;

/**
 * Created by Wojciech_Soltys on 24.11.2016.
 */
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User getUserByEmail(@Nonnull String email) {
        final String SELECT_SQL = "select * from users where email = ?";
        User user = (User) jdbcTemplate.queryForObject(
                SELECT_SQL,
                new Object[]{email},
                new UserRowMapper());
        return user;
    }

    @Override
    public User saveUser(@Nonnull User user) {
        final String INSERT_SQL = "INSERT INTO users (firstName,lastName,email) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_SQL, new String[] {"id"});
                    ps.setString(1, user.getFirstName());
                    ps.setString(2, user.getLastName());
                    ps.setString(3, user.getEmail());
                    return ps;
                },
                keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    @Override
    public void removeUser(@Nonnull User user) {
        final String DELETE_SQL = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(DELETE_SQL, new Object[]{user.getId()});
    }

    @Override
    public User getById(@Nonnull Long id) {
        final String SELECT_SQL = "select * from users where id = ?";
        User user = (User) jdbcTemplate.queryForObject(
                SELECT_SQL,
                new Object[]{id},
                new UserRowMapper());
        return user;
    }

    @Override
    public Collection<User> getAll() {
        final String SELECT_SQL = "select * from users";
        return jdbcTemplate.query(
                SELECT_SQL,
                new UserRowMapper());

    }
}
