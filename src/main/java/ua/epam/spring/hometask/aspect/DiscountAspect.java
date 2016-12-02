package ua.epam.spring.hometask.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import ua.epam.spring.hometask.domain.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wojciech_Soltys on 02.12.2016.
 */
@Aspect
public class DiscountAspect {
    private long discountCount = 0;
    private Map<User, Integer> eventsDiscountCounter = new HashMap<>();

    @Before("execution(* ua.epam.spring.hometask.service.DiscountService.getDiscount(..))")
    public void countBeforeGetDiscount(JoinPoint joinPoint) {
        discountCount++;
        System.out.println("ALL DISCOUNT COUNT: " + discountCount);
    }

    @Before("execution(* ua.epam.spring.hometask.service.DiscountService.getDiscount(..)) && args(user,..)")
    public void countBeforeGetDiscountForEvent(JoinPoint joinPoint, User user) {
        if (!eventsDiscountCounter.containsKey(user)) {
            eventsDiscountCounter.put(user, 0);
        }
        eventsDiscountCounter.put(user, eventsDiscountCounter.get(user)+1);
        System.out.println("DISCOUNT COUNT FOR USER " + user.getFirstName() + " " + user.getLastName() + " " + discountCount);
    }
}
