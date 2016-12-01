package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * Created by Wojciech_Soltys on 30.11.2016.
 */
public interface EventDao {

    @Nullable
    Event getByName(@Nonnull String name);

    Event save(@Nonnull Event object);

    void remove(@Nonnull Event object);

    Event getById(@Nonnull Long id);

    @Nonnull
    Collection<Event> getAll();
}
