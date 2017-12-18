package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
@RequestMapping("/meals")
public class MealController {
    private static final Logger log = getLogger(MealController.class);

    @Autowired
    private MealService mealService;

    @GetMapping
    public String update(Model model, @RequestParam(value = "action", required = false) String action, @RequestParam(value = "id", required = false) Integer id) {
        int userId = AuthorizedUser.id();
        if ("delete".equals(action)) {
            mealService.delete(id, userId);

        } else if ("update".equals(action)){
            Meal meal = mealService.get(id, userId);
            assureIdConsistent(meal, id);
            log.info("update {} for user {}", meal, userId);
            model.addAttribute("meal", meal);
            return "mealForm";
        } else if ("create".equals(action)) {
            Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
            log.info("create {} for user {}", meal, userId);
            model.addAttribute("meal", meal);
            return "mealForm";
        }

        List<MealWithExceed> list = MealsUtil.getWithExceeded(mealService.getAll(userId), AuthorizedUser.getCaloriesPerDay());
        model.addAttribute("meals", list);

        return "meals";
    }

    @PostMapping
    public String setMeal(HttpServletRequest request) {
        String action = request.getParameter("action");
        if (action == null) {
            Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));

            if (request.getParameter("id").isEmpty()){
                mealService.create(meal, AuthorizedUser.id());
            } else {
                meal.setId(Integer.parseInt(request.getParameter("id")));
                mealService.update(meal, AuthorizedUser.id());
            }
        } else if ("filter".equals(action)) {
            LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
            LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
            LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
            LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
            request.setAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
            return "meals";
        }

        return "redirect:meals";
    }

    private List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = AuthorizedUser.id();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Meal> mealsDateFiltered = mealService.getBetweenDates(
                startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId);

        return MealsUtil.getFilteredWithExceeded(mealsDateFiltered,
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        );
    }

}
