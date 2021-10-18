package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

@Service
public class MealService {

    @Autowired
    private MealRepository repository;
    private final Integer authorisedUser = SecurityUtil.authUserId();

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        if (checkUserId(meal)) {
            return repository.save(meal);
        }
        return null;
    }

    public void delete(int idMeal) {
        Meal meal = ValidationUtil.checkNotFoundWithId(repository.get(idMeal), idMeal);
        if (checkUserId(meal)) {
            ValidationUtil.checkNotFoundWithId(repository.delete(idMeal), idMeal);
        }
    }

    public Meal get(int idMeal) {
        Meal meal = ValidationUtil.checkNotFoundWithId(repository.get(idMeal), idMeal);
        if (checkUserId(meal)) {
            return repository.get(idMeal);
        }
        return null;
    }

    public Collection<Meal> getListByUser(Integer idUser) {
        if(idUser.equals(authorisedUser)){
            return  repository.getListByUserId(idUser);
        } else {
            throw new NotFoundException("Пользователь не найден");
        }
    }

    public Collection<Meal> getAll(){
        return repository.getAll();
    }

    public void update(Meal meal){
        if(checkUserId(meal)){
            ValidationUtil.checkNotFoundWithId(repository.save(meal),meal.getId());
        }
    }

    private boolean checkUserId(Meal meal) {
        return meal.getIdUser() != null && meal.getIdUser().equals(authorisedUser);
    }

}