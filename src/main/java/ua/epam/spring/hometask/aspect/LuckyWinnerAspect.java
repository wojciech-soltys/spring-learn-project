package ua.epam.spring.hometask.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.service.BookingService;

import java.util.Random;
import java.util.Set;

/**
 * Created by Wojciech_Soltys on 02.12.2016.
 */
@Aspect
public class LuckyWinnerAspect {

    private BookingService bookingService;

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    private static Random random = new Random();

    @Before("execution(* bookTickets(..)) && args(tickets)")
    public void checkIfTicketIsLucky(JoinPoint joinPoint, Set<Ticket> tickets) {
        System.out.println("test");
        tickets.forEach((value) -> value.setLuckyTicket(random.nextBoolean()));
    }
}
