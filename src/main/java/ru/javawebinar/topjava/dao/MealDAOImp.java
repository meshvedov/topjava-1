package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class MealDAOImp implements MealDAO {

    private ConcurrentMap<Integer, Meal> mealConcurrentMap;

    public MealDAOImp() {
        this.mealConcurrentMap = MealDB.mealConcurrentMap;
    }

    @Override
    public void addMeal(Meal meal) {
        mealConcurrentMap.put(meal.getId(), meal);
    }

    @Override
    public void deleteMeal(int id) {
        mealConcurrentMap.remove(id);
    }

    @Override
    public void updateMeal(Meal meal) {
        mealConcurrentMap.replace(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAllMeals() {
        List<Meal> list = new ArrayList<>();
        mealConcurrentMap.forEach((k, v) -> list.add(v));
        return list;
    }

    @Override
    public Meal getMealById(int id) {
        return mealConcurrentMap.get(id);
    }
}
