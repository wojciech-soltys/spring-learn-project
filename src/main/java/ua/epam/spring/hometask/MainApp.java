package ua.epam.spring.hometask;

/**
 * Created by Wojciech_Soltys on 22.11.2016.
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.User;
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

        EventService eventService = (EventServiceImpl) context.getBean("eventServiceImpl");
        System.out.println(eventService.getByName("EVENT"));

        System.out.println(eventService.getById(1L));

        System.out.println(eventService.getAll());

        Event event = new Event();
        event.setName("EVENT3");
        event.setBasePrice(10.0);
        event.setRating(EventRating.LOW);
        event.getAuditoriums().put(LocalDateTime.of(2016, Month.DECEMBER, 15, 20, 30) , auditorium);

        eventService.save(event);
        System.out.println(eventService.getAll());

        //eventService.remove(event);
        //System.out.println(eventService.getAll());

        BookingService bookingServiceService = (BookingServiceImpl) context.getBean("bookingServiceImpl");
        System.out.println(bookingServiceService.getTicketsPrice(event, LocalDateTime.of(2016, Month.DECEMBER, 15, 20, 30),
                saveUser, new HashSet<>(Arrays.asList(new Integer[]{1,2,5,6}))));
    }
}
