package ua.epam.spring.hometask.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import ua.epam.spring.hometask.domain.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Wojciech_Soltys on 02.12.2016.
 */
@Aspect
public class DiscountAspect {
    private long discountCount = 0;
    private Map<User, Long> eventsDiscountCounter = new ConcurrentHashMap<>();

    @Before("execution(* ua.epam.spring.hometask.service.DiscountService.getDiscount(..))")
    public synchronized void countBeforeGetDiscount(JoinPoint joinPoint) {
        discountCount++;
        System.out.println("ALL DISCOUNT COUNT: " + discountCount);
    }

    @Before("execution(* ua.epam.spring.hometask.service.DiscountService.getDiscount(..)) && args(user,..)")
    public void countBeforeGetDiscountForEvent(JoinPoint joinPoint, User user) {
        eventsDiscountCounter.compute(user, (k,v) -> (v==null ? 1 : v+1));
        System.out.println("DISCOUNT COUNT FOR USER " + user.getFirstName() + " " + user.getLastName() + ": "
                + eventsDiscountCounter.get(user));
    }
}
