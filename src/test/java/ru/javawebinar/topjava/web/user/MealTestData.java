package ru.javawebinar.topjava.web.user;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 2;

    public static final List<Meal> meals = Arrays.asList(
            new Meal(100002, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(100003,LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(100006,LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(100007,LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(100008,LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 500)
    );

    public static Meal getUpdatedMeal() {
        Meal updated = new Meal (meals.get(6));
        updated.setDescription("Updated");
        updated.setCalories(222);
        updated.setDateTime(LocalDateTime.now());
        return updated;
    }

    public static Meal getNewMeal() {
        return new Meal(LocalDateTime.now(), "New", 1111);
    }
}
