package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;

public interface MealDAO {
    void addMeal(Meal meal);
    void deleteMeal(int id);
    void updateMeal(Meal meal);
    List<Meal> getAllMeals();
    Meal getMealById(int id);
}
