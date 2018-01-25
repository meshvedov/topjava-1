package ru.javawebinar.topjava.web.meal;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class MealRestControllerTest extends AbstractControllerTest {

    private static final String URL = "/meals";

    @Autowired
    private MealService mealService;

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(URL + "/" + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(mealService.getAll(USER_ID), Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = getUpdated();
        mockMvc.perform(put(URL + "/" + MEAL1_ID).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(mealService.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Meal expected = getCreated();
        ResultActions actions = mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());
        Meal actual = TestUtil.readFromJson(actions, Meal.class);
        expected.setId(actual.getId());
        assertMatch(actual, expected);
    }

//    @Test
//    public void testGetBetween() throws Exception {
//        List<MealWithExceed> expected = MealsUtil.getWithExceeded(Arrays.asList(MEAL3, MEAL2, MEAL1), MealsUtil.DEFAULT_CALORIES_PER_DAY);
//        ResultActions actions = mockMvc.perform(get(URL + "/between")
//                .param("startLDT", LocalDateTime.of(2015, 5, 30, 9, 0).toString())
//                .param("endLDT", LocalDateTime.of(2015, 5, 30, 21, 0).toString()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(model().attribute("mealWithExceedList", expected));
//    }

    @Test
    public void testBetween() throws Exception {
        List<MealWithExceed> expected = MealsUtil.getWithExceeded(Arrays.asList(MEAL3, MEAL2, MEAL1), MealsUtil.DEFAULT_CALORIES_PER_DAY);
        ResultActions actions = mockMvc.perform(get(URL + "/between")
                .param("startDate", "2015-05-30")
                .param("endDate", "2015-05-30"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

    }

    @Test
    public void testGet() throws Exception {
    }

}