package ua.epam.spring.hometask;

/**
 * Created by Wojciech_Soltys on 22.11.2016.
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.service.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("Beans.xml");

        UserService userService = (UserServiceImpl) context.getBean("userServiceImpl");
        User getUser = userService.getUserByEmail("mkyong@gmail.com");
        System.out.println(getUser);

        User saveUser = new User();
        saveUser.setFirstName("firstName");
        saveUser.setLastName("lastName");
        saveUser.setEmail("email");
        saveUser = userService.save(saveUser);
        System.out.println(saveUser);

        User saveUser1 = new User();
        saveUser1.setFirstName("firstName1");
        saveUser1.setLastName("lastName1");
        saveUser1.setEmail("email1");
        saveUser1 = userService.save(saveUser1);
        System.out.println(saveUser1);

        //userService.remove(saveUser);

        System.out.println(userService.getAll());

        AuditoriumService auditoriumService = (AuditoriumServiceImpl) context.getBean("auditoriumServiceImpl");
        System.out.println(auditoriumService.getById(1L));

        System.out.println(auditoriumService.getByName("A"));

        Auditorium auditorium = new Auditorium();
        auditorium.setName("B");
        auditorium.setNumberOfSeats(200);
        auditorium.setVipSeats(new HashSet<>(Arrays.asList(new Integer[]{5,6,7,8,9,10})));

        auditoriumService.save(auditorium);
        System.out.println(auditoriumService.getAll());

        auditoriumService.remove(auditorium);
        System.out.println(auditoriumService.getAll());

        auditoriumService.save(auditorium);
        System.out.println(auditoriumService.getAll());

        EventService eventService = (EventService) context.getBean("eventServiceImpl");
        System.out.println(eventService.getByName("EVENT"));
        System.out.println(eventService.getByName("EVENT"));

        System.out.println(eventService.getById(1L));

        System.out.println(eventService.getAll());

        LocalDateTime dateTime = LocalDateTime.of(2016, Month.DECEMBER, 15, 20, 30);
        LocalDateTime dateTime1 = LocalDateTime.of(2016, Month.DECEMBER, 15, 21, 30);
        Event event = new Event();
        event.setName("EVENT3");
        event.setBasePrice(10.0);
        event.setRating(EventRating.LOW);
        event.getAuditoriums().put(dateTime, auditorium);

        eventService.save(event);
        System.out.println(eventService.getAll());

        Event event1 = new Event();
        event1.setName("EVENT3");
        event1.setBasePrice(12.0);
        event1.setRating(EventRating.MID);
        event1.getAuditoriums().put(dateTime1, auditorium);

        //eventService.remove(event);
        //System.out.println(eventService.getAll());

        BookingService bookingServiceService = (BookingService) context.getBean("bookingServiceImpl");
        System.out.println(bookingServiceService.getTicketsPrice(event, dateTime, saveUser,
                new HashSet<>(Arrays.asList(new Integer[]{1,2,5,6}))));
        System.out.println(bookingServiceService.getTicketsPrice(event, dateTime, saveUser,
                new HashSet<>(Arrays.asList(new Integer[]{1,2,5,6}))));

        Ticket ticket = new Ticket(saveUser, event, dateTime, 1L);
        Ticket ticket1 = new Ticket(saveUser1, event1, dateTime1, 2L);
        //Set<Ticket> tickets = new HashSet<>(Arrays.asList(new Ticket[]{ticket, ticket1}));
        //bookingServiceService.bookTickets(tickets);
        bookingServiceService.bookTicket(ticket);
        System.out.println(ticket.getUser().getLuckyTickets());
    }
}
