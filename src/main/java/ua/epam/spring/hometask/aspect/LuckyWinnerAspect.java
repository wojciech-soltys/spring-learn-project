package ua.epam.spring.hometask.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;

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

    @Before("execution(* bookTicket(..)) && args(ticket)")
    public void checkIfUserIsLucky(JoinPoint joinPoint, Ticket ticket) {
        User user = ticket.getUser();
        if(user.isLuckyTicketForUser(ticket)) {
            System.out.println("LUCKY WINNER ASPECT - YOU ARE LUCKY TICKET PRICE IS 0");
            bookingService.bookTicket(ticket);
        }
    }
}
