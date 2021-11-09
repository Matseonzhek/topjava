package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Profile("datajpa")
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUser(crudRepository.getUserById(userId));
        if (meal.isNew()) {
            crudRepository.save(meal);
        } else if (meal.getUser().getId() == null) {
            return null;
        }
        Meal newMeal = new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories());
        crudRepository.save(newMeal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = crudRepository.getMealById(id);
        return meal != null && meal.getUser().getId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAllBy(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.findByDateTimeGreaterThanEqualAndDateTimeLessThanAndUserIdEquals(startDateTime, endDateTime, userId);
    }
}
