package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Auditorium;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * Created by Wojciech_Soltys on 28.11.2016.
 */
public interface AuditoriumDao {
    Auditorium save(@Nonnull Auditorium object);
    void remove(@Nonnull Auditorium object);
    Auditorium getById(@Nonnull Long id);
    Set<Auditorium> getAll();
    Auditorium getByName(@Nonnull String name);
}
