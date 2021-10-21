package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

@Controller
public class MealRestController {


    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public void delete(int idMeal) {
        service.delete(idMeal);
    }

    public Meal get(int idMeal) {
        return service.get(idMeal);
    }

    public Collection<Meal> getListByUser(Integer userId) {
        return service.getListByUser(userId);
    }

    public Collection<Meal> getAll() {
        return service.getAll();
    }

    public void update(Meal meal) {
        service.create(meal);
    }

}