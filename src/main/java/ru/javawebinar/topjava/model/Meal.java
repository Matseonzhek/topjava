package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal extends AbstractBaseEntity {
    private Integer id;

    private Integer idUser;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(Integer idUser,LocalDateTime dateTime, String description, int calories) {
        this(null, idUser, dateTime, description, calories);
    }

    public Meal(Integer id, Integer idUser, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.idUser = idUser;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Integer getIdUser() {return idUser;}

    public void setIdUser(Integer idUser) {this.idUser = idUser;}

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
