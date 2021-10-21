package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private final Integer authorisedUser = SecurityUtil.authUserId();

    {
        for (Meal meal : MealsUtil.meals) {
            save(meal);
        }
    }

    @Override
    public Meal save(Meal meal) {
        if(checkUserId(meal)){
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(authorisedUser);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);}
        return null;
    }

    @Override
    public boolean delete(int mealId) {
        if (checkUserId(repository.get(mealId))) {
            return repository.remove(mealId) != null;
        }
        return false;
    }

    @Override
    public Meal get(int mealId) {
        if (checkUserId(repository.get(mealId))) {
            return repository.get(mealId);
        }
        return null;
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }

    @Override
    public List<Meal> getListByUserId(Integer userId) {
        if(Objects.equals(userId, authorisedUser)){
        Collection<Meal> nonSortedMealList = getAll();
        return nonSortedMealList.stream()
                .filter(meal -> userId.equals(meal.getUserId()))
                .sorted(Comparator.comparing(Meal::getDate))
                .collect(Collectors.toList());}
        return null;
    }

    @Override
    public boolean checkUserId(Meal meal) {
        return meal.getUserId() != null && meal.getUserId().equals(authorisedUser);
    }
}

