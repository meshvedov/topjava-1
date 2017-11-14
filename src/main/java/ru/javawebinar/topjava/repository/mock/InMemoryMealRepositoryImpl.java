package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.*;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        if (repository.get(id).getUserId() == userId) {
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.get(id).getUserId() == userId ? repository.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Collection<Meal> mealForUserId = repository.values();
        return mealForUserId.stream().
                filter(x -> x.getUserId() == userId).
                sorted(Comparator.comparing(Meal::getDateTime).reversed()).
                collect(Collectors.toCollection(ArrayList::new));
    }

    public Collection<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        return getAll(userId).stream().
                filter(x -> isBetween(x.getDate(), startDate, endDate)).
                sorted(Comparator.comparing(Meal::getDateTime)).
                collect(Collectors.toCollection(ArrayList::new));
    }

    public Collection<Meal> getAll(int userId, LocalTime startTime, LocalTime endTime) {
        return getAll(userId).stream().
                filter(x -> isBetween(x.getTime(), startTime, endTime)).
                sorted(Comparator.comparing(Meal::getDateTime)).
                collect(Collectors.toCollection(ArrayList::new));
    }
}

