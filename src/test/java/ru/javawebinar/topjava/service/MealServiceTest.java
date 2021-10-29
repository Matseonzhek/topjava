package ru.javawebinar.topjava.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Test
    public void get() {
        Meal meal = mealService.get(MEAL_ID, USER_ID);
        assertThat(meal).usingRecursiveComparison().isEqualTo(meals.get(0));
    }

    @Test(expected = NotFoundException.class)
    public void getOtherUserMeal() {
        Meal meal = mealService.get(MEAL_ID, ADMIN_ID);
        assertThat(meal).usingRecursiveComparison().isEqualTo(meals.get(0));
    }

    @Test
    public void delete() {
        mealService.delete(100002, USER_ID);
        assertThrows(NotFoundException.class, ()->mealService.get(100002,USER_ID));
    }


    @Test(expected = NotFoundException.class)
    public void deleteOtherUserMeal() {
        mealService.delete(100002, ADMIN_ID);
        assertThrows(NotFoundException.class, ()->mealService.get(100000,USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void update() {
        Meal updated = getUpdatedMeal();
        mealService.update(updated,ADMIN_ID);
        assertThat(mealService.get(100008,100001)).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(updated);
    }

    @Test
    public void create() {
        Meal created = mealService.create(getNewMeal(),USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNewMeal();
        newMeal.setId(newId);
        assertThat(created).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(newMeal);
        assertThat(mealService.get(100009,USER_ID)).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(newMeal);

    }
}