package ua.epam.spring.hometask.service;

import org.springframework.beans.factory.annotation.Autowired;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by Wojciech_Soltys on 01.12.2016.
 */
public class BookingServiceImpl implements BookingService {

    private DiscountService discountService;

    @Autowired
    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Integer> seats) {
        double basePrice = event.getBasePrice();
        Auditorium auditorium = event.getAuditoriums().get(dateTime);
        long countVipSeats = auditorium.countVipSeats(seats);
        long numberOfNormalSeats = seats.size() - countVipSeats;
        double discount = (100 - discountService.getDiscount(user, event,dateTime, seats.size()))/100;
        double ticketsPrice = Math.round(((basePrice * numberOfNormalSeats) + (basePrice * countVipSeats)*2) * discount * 100.0) / 100.0;
        return ticketsPrice;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {

    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return null;
    }
}
