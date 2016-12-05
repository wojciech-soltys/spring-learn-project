package ua.epam.spring.hometask.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Wojciech_Soltys on 01.12.2016.
 */
@Aspect
public class CounterAspect {
    private Map<Event, Long> counterEventByName = new ConcurrentHashMap<>();
    private Map<Event, Long> counterGetEventPrice = new ConcurrentHashMap<>();
    private Map<Event, Long> counterBookEvent = new ConcurrentHashMap<>();

    @AfterReturning(
            pointcut = "execution(* ua.epam.spring.hometask.service.EventService.getByName(..))",
            returning = "retVal")
    public void countBeforeGetEventByName(Object retVal) {
        Event event = (Event) retVal;
        counterEventByName.compute(event, (k,v) -> (v==null ? 1 : v+1));
        System.out.println("GET BY NAME COUNT FOR " + event.getName() + ": " + counterEventByName.get(event));
    }

    @Before("execution(* ua.epam.spring.hometask.service.BookingService.getTicketsPrice(..)) && args(event,..)")
    public void countAfterGetEventPrice(JoinPoint joinPoint, Event event) {
        counterGetEventPrice.compute(event, (k,v) -> (v==null ? 1 : v+1));
        System.out.println("GET PRICE COUNT FOR EVENT " + event.getName() + ": " + counterGetEventPrice.get(event));
    }

    @Before("execution(* ua.epam.spring.hometask.service.BookingService.bookTickets(..)) && args(tickets)")
    public void countAfterGetEventPrice(JoinPoint joinPoint, Set<Ticket> tickets) {
        tickets.forEach((value) -> {
            Event event = value.getEvent();
            counterBookEvent.compute(event, (k,v) -> (v==null ? 1 : v+1));
            System.out.println("GET BOOK TICKET COUNT FOR EVENT " + event.getName() + ": " + counterBookEvent.get(event));
        });
    }
}
