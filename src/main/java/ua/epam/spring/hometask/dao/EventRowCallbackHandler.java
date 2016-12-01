package ua.epam.spring.hometask.dao;

import org.springframework.jdbc.core.RowCallbackHandler;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Wojciech_Soltys on 30.11.2016.
 */
public class EventRowCallbackHandler implements RowCallbackHandler {

    private Collection<Event> events = new ArrayList<>();

    public Collection<Event> getEvents() {
        return events;
    }

    private Event currentEvent;

    private Long currentEventAirDateId;

    private Auditorium currentAuditorium;

    @Override
    public void processRow(ResultSet resultSet) throws SQLException {
        Long eventId = resultSet.getLong("id");
        if(currentEvent == null || !eventId.equals(currentEvent.getId())) {
            String name = resultSet.getString("name");
            Double basePrice = resultSet.getDouble("basePrice");
            EventRating eventRating = EventRating.valueOf(resultSet.getString("rating"));

            currentEvent = new Event();
            currentEvent.setId(eventId);
            currentEvent.setName(name);
            currentEvent.setBasePrice(basePrice);
            currentEvent.setRating(eventRating);
            events.add(currentEvent);
            currentEventAirDateId = null;
        }

        Long eventAirDateId = resultSet.getLong("eventAirDates.id");
        Long auditoriumId = resultSet.getLong("auditorium.id");
        if(!resultSet.wasNull() && (currentEventAirDateId == null || !eventAirDateId.equals(currentEventAirDateId))) {
            currentEventAirDateId = eventAirDateId;
            Timestamp timestamp = resultSet.getTimestamp("eventAirDates.airDate");
            LocalDateTime localDateTime = timestamp.toLocalDateTime();

            String auditoriumName = resultSet.getString("auditorium.name");
            Long auditoriumNumberOfSeats = resultSet.getLong("auditorium.numberOfSeats");

            currentAuditorium = new Auditorium();
            currentAuditorium.setId(auditoriumId);
            currentAuditorium.setName(auditoriumName);
            currentAuditorium.setNumberOfSeats(auditoriumNumberOfSeats);
            currentEvent.addAirDateTime(localDateTime, currentAuditorium);
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
