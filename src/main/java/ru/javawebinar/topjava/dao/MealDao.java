package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealDao {
    private final List<Meal> mealDao = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    private final List<MealTo> mealToList = MealsUtil.groupedMeal(getMeals());

    public List<Meal> getMeals() {
        return mealDao;
    }

    public List<MealTo> getMealToList() {
        return mealToList;
    }

    public void deleteMealRecord(int idMeal) {
        int index=0;
        for (Meal meal: mealDao) {
            if(meal.getId()==idMeal) {
                index = mealDao.indexOf(meal);
                break;
            }
        }
        mealDao.remove(index);
    }

    public void createMealRecord(Meal meal) {
        mealDao.add(meal);
    }

    public void updateMealRecord(int idMeal, LocalDateTime localDateTime, String description, int calories) {
        int index=0;
        for (Meal meal: mealDao) {
            if(meal.getId()==idMeal) {
                index = mealDao.indexOf(meal);
                break;
            }
        }
        Meal meal = mealDao.get(index);
        meal.setCalories(calories);
        meal.setDateTime(localDateTime);
        meal.setDescription(description);
    }
}
