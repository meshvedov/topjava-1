package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/meals")
public class MealRestController extends AbstractMealController {

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        super.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable Integer id) {
        super.update(meal, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createMeal(@RequestBody Meal meal) {
        Meal created = super.create(meal);
        return new ResponseEntity<Meal>(created, HttpStatus.CREATED);
    }

//    @GetMapping(value = "/{startLDT}/{endLDT}", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/between", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween( @DateTimeFormat LocalDateTime start,
                                            @DateTimeFormat LocalDateTime end) {
        List<MealWithExceed> withExceeds = super.getBetween(start.toLocalDate(), start.toLocalTime(), end.toLocalDate(), end.toLocalTime());
        return withExceeds;
    }

    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

}