package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final Meal MEAL1_USER = new Meal(100_002, LocalDateTime.of(2017, 11, 22, 9, 00), "breakfast", 500);
    public static final Meal MEAL2_USER = new Meal(100_003, LocalDateTime.of(2017, 11, 22, 12, 00), "dinner", 1000);
    public static final Meal MEAL1_ADMIN = new Meal(100_004, LocalDateTime.of(2017, 11, 21, 10, 00), "lunch", 1500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "datetime");
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("datetime").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... meals) {
        assertMatch(actual, Arrays.asList(meals));
    }
}
