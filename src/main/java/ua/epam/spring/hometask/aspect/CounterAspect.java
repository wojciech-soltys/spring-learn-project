package ua.epam.spring.hometask.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Wojciech_Soltys on 01.12.2016.
 */
@Aspect
public class CounterAspect {
    private Map<String, Integer> counter = new HashMap<>();

    private final static String COUNT_BY_NAME = "COUNT_BY_NAME";
    private final static String COUNT_GET_PRICE = "COUNT_GET_PRICE";
    private final static String COUNT_BOOK_TICKET = "COUNT_BOOK_TICKET";

    @Before("execution(* ua.epam.spring.hometask.service.EventService.getByName(..))")
    public void countBeforeGetEventByName(JoinPoint joinPoint) {
        if (!counter.containsKey(COUNT_BY_NAME)) {
            counter.put(COUNT_BY_NAME, 0);
        }
        counter.put(COUNT_BY_NAME, counter.get(COUNT_BY_NAME)+1);
        System.out.println("GET BY NAME COUNT: " + counter.get(COUNT_BY_NAME));
    }

    @Before("execution(* ua.epam.spring.hometask.service.BookingService.getTicketsPrice(..)) && args(event,..)")
    public void countAfterGetEventPrice(JoinPoint joinPoint, Event event) {
        final String key = COUNT_GET_PRICE + event.getName();
        if (!counter.containsKey(key)) {
            counter.put(key, 0);
        }
        counter.put(key, counter.get(key)+1);
        System.out.println("GET PRICE COUNT FOR EVENT " + event.getName() + " : " + counter.get(key));
    }

    @Before("execution(* ua.epam.spring.hometask.service.BookingService.bookTickets(..)) && args(tickets)")
    public void countAfterGetEventPrice(JoinPoint joinPoint, Set<Ticket> tickets) {
        tickets.forEach((v) -> {
            Event event = v.getEvent();
            String key = COUNT_BOOK_TICKET + event.getName();
            if (!counter.containsKey(key)) {
                counter.put(key, 0);
            }
            counter.put(key, counter.get(key)+1);
            System.out.println("GET BOOK TICKET COUNT FOR EVENT " + event.getName() + " : " + counter.get(key));
        });
    }
}
