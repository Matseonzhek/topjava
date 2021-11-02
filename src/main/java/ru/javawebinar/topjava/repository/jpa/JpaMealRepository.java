package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            User user = entityManager.getReference(User.class, userId);
            meal.setUser(user);
            entityManager.persist(meal);
            return meal;
        } else {
            User user = entityManager.getReference(User.class, userId);
            meal.setUser(user);
            entityManager.merge(meal);
            return meal;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Meal meal = get(id, userId);
        entityManager.remove(meal);
        return meal == null;
    }

    @Override
    @Transactional
    public Meal get(int id, int userId) {
        Meal meal = entityManager.find(Meal.class, id);
        User user = entityManager.getReference(User.class, userId);
        meal.setUser(user);
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return entityManager.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("id", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return entityManager.createNamedQuery(Meal.MEAL_FILTERED, Meal.class)
                .setParameter("startdate", startDateTime)
                .setParameter("enddate", endDateTime)
                .setParameter("id", userId)
                .getResultList();
    }
}