package ru.javawebinar.topjava.service;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.MethodAccessor_Long;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})

@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceImplTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void get() throws Exception {
        Meal meal = mealService.get(MEAL1_USER.getId(), 100000);
//        assertEquals(meal, MEAL);
        assertMatch(meal, MEAL1_USER);
    }

    @Test
    public void delete() throws Exception {
        mealService.delete(MEAL1_USER.getId(), UserTestData.USER_ID);
        assertMatch(mealService.getAll(UserTestData.USER_ID), MEAL2_USER);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> list = mealService.getBetweenDateTimes(LocalDateTime.of(2017, 11, 21, 0, 0),
                LocalDateTime.of(2017, 11, 21, 10, 0), ADMIN_ID);
        assertMatch(list, MEAL1_ADMIN);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> meals = mealService.getAll(USER_ID);
        assertMatch(meals, Arrays.asList(MEAL2_USER, MEAL1_USER));
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL1_USER);
        updated.setCalories(100);
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(MEAL1_USER.getId(), USER_ID), updated);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "break", 100);
        Meal created = mealService.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(mealService.getAll(USER_ID), created, MEAL2_USER, MEAL1_USER);
    }

}