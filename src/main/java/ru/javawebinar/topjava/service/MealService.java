package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealService {
    Meal create(Meal meal);

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    void update(Meal meal);

    Collection<Meal> getAll(int userId) throws NotFoundException;

    Collection<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) throws NotFoundException;
    Collection<Meal> getAll(int userId, LocalTime startTime, LocalTime Time) throws NotFoundException;
}