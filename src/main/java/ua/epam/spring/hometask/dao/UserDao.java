package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Created by Wojciech_Soltys on 24.11.2016.
 */
public interface UserDao {
    User getUserByEmail(@Nonnull String email);
    User saveUser(@Nonnull User user);
    void removeUser(@Nonnull User user);
    User getById(@Nonnull Long id);
    Collection<User> getAll();
}
