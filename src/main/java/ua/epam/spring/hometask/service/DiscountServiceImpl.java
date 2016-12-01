package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Created by Wojciech_Soltys on 01.12.2016.
 */
public class DiscountServiceImpl implements DiscountService {
    @Override
    public double getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        Random generator = new Random();
        if(numberOfTickets > 100) {
            numberOfTickets = 100;
        }
        return generator.nextDouble() * numberOfTickets;
    }
}
