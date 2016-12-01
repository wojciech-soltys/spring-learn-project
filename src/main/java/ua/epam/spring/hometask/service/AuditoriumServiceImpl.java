package ua.epam.spring.hometask.service;

import org.springframework.beans.factory.annotation.Autowired;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

/**
 * Created by Wojciech_Soltys on 28.11.2016.
 */
public class AuditoriumServiceImpl implements AuditoriumService {

    private AuditoriumDao auditoriumDao;

    @Autowired
    public void setAuditoriumDao(AuditoriumDao auditoriumDao) {
        this.auditoriumDao = auditoriumDao;
    }

    @Override
    public Auditorium save(@Nonnull Auditorium object) {
        return auditoriumDao.save(object);
    }

    @Override
    public void remove(@Nonnull Auditorium object) {
        auditoriumDao.remove(object);
    }

    @Override
    public Auditorium getById(@Nonnull Long id) {
        return auditoriumDao.getById(id);
    }

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return auditoriumDao.getAll();
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return auditoriumDao.getByName(name);
    }
}
