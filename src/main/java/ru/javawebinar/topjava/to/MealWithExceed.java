package ru.javawebinar.topjava.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class MealWithExceed {
    private final Integer id;
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    @JsonCreator
    public MealWithExceed(@JsonProperty("id") Integer id, @JsonProperty("dateTime") LocalDateTime dateTime, @JsonProperty("description") String description, @JsonProperty("calories") int calories, @JsonProperty("exceed") boolean exceed) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExceed() {
        return exceed;
    }

    @Override
    public String toString() {
        return "MealWithExceed{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MealWithExceed that = (MealWithExceed) o;

        if (calories != that.calories) return false;
        if (exceed != that.exceed) return false;
        if (!id.equals(that.id)) return false;
        if (!dateTime.equals(that.dateTime)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}