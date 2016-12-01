package ua.epam.spring.hometask.dao;

import org.springframework.jdbc.core.RowCallbackHandler;
import ua.epam.spring.hometask.domain.Auditorium;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Wojciech_Soltys on 28.11.2016.
 */
public class AuditoriumRowCallbackHandler implements RowCallbackHandler {

    private List<Auditorium> auditoriums = new ArrayList<>();

    public List<Auditorium> getAll() {
        return auditoriums;
    }

    public Auditorium getFirst() {
        if (auditoriums.size() == 0) {
            return null;
        }
        return auditoriums.get(0);
    }

    private Auditorium currentAuditorium;

    @Override
    public void processRow(ResultSet resultSet) throws SQLException {
        Long auditoriumId = resultSet.getLong("id");
        if (currentAuditorium == null || auditoriumId != currentAuditorium.getId()) {
            String name = resultSet.getString("name");
            Long numberOfSeats = resultSet.getLong("numberOfSeats");

            currentAuditorium = new Auditorium();
            currentAuditorium.setId(auditoriumId);
            currentAuditorium.setName(name);
            currentAuditorium.setNumberOfSeats(numberOfSeats);
            auditoriums.add(currentAuditorium);
        }

        if(!resultSet.wasNull()) {
            Integer vipSeatNumber = resultSet.getInt("numberOfSeat");
            Set<Integer> vipSeats = currentAuditorium.getVipSeats();
            if(vipSeats == null || vipSeats.isEmpty()) {
                currentAuditorium.setVipSeats(new HashSet<>());
                vipSeats = currentAuditorium.getVipSeats();
            }
            vipSeats.add(vipSeatNumber);
            System.out.println("");
        }
    }
}
