package ua.epam.spring.hometask.service;

import org.springframework.beans.factory.annotation.Autowired;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * Created by Wojciech_Soltys on 29.11.2016.
 */
public class EventServiceImpl implements EventService {

    private EventDao eventDao;

    @Autowired
    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        return eventDao.getByName(name);
    }

    @Override
    public Event save(@Nonnull Event object) {
        return eventDao.save(object);
    }

    @Override
    public void remove(@Nonnull Event object) {
        eventDao.remove(object);
    }

    @Override
    public Event getById(@Nonnull Long id) {
        return eventDao.getById(id);
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return eventDao.getAll();
    }
}
