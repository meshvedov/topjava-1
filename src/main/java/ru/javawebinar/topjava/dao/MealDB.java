package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDB {

//    public static List<Meal> meals = Arrays.asList(
//            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
//            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
//            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
//            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
//            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
//            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
//    );

    public static ConcurrentMap<Integer, Meal> mealConcurrentMap = new ConcurrentHashMap<>();
    static {
        mealConcurrentMap.put(1, new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealConcurrentMap.put(2, new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealConcurrentMap.put(3, new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealConcurrentMap.put(4, new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealConcurrentMap.put(5, new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealConcurrentMap.put(6, new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public static AtomicInteger atomicInteger = new AtomicInteger(mealConcurrentMap.size());
}