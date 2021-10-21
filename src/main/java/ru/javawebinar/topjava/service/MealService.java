package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.Collection;

@Service
public class MealService {


    private MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    public void delete(int idMeal) {
        ValidationUtil.checkNotFoundWithId(repository.delete(idMeal), idMeal);
    }

    public Meal get(int idMeal) {
        return ValidationUtil.checkNotFoundWithId(repository.get(idMeal), idMeal);
    }

    public Collection<Meal> getListByUser(Integer userId) {
        return repository.getListByUserId(userId);
    }

    public Collection<Meal> getAll() {
        return repository.getAll();
    }

    public void update(Meal meal) {
        ValidationUtil.checkNotFoundWithId(repository.save(meal), meal.getId());
    }

}