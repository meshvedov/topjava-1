package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    private static final Sort SORT_DATE = new Sort(Sort.Direction.DESC, "dateTime");

    @Autowired
    private CrudMealRepository crudRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        Meal m = getAll(userId).get(0);
        meal.setUser(m.getUser());
        return crudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal m = crudRepository.findById(id).orElse(null);
        if (m != null && m.getUser().getId() == userId)
            return m;
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> all = crudRepository.findAll(SORT_DATE).stream().filter(meal -> meal.getUser().getId() == userId).collect(Collectors.toList());
        return all;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.getBetweenFromMeal(startDate, endDate, userId);
    }
}
