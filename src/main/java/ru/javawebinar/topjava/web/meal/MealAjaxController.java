package ru.javawebinar.topjava.web.meal;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.awt.*;
import java.time.LocalDateTime;
import ru.javawebinar.topjava.to.MealWithExceed;
import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("ajax/profile/meals")
public class MealAjaxController extends AbstractMealController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id")int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpd(@RequestParam(value = "id", required = false) int id,
                            @RequestParam(value = "dateTime", required = false)LocalDateTime ldt,
                            @RequestParam(value = "description", required = false) String description,
                            @RequestParam(value = "calories", required = false) int calories) {

        Meal meal = new Meal(id, ldt, description, calories);
        if (meal.isNew())
            super.create(meal);
    }
}
