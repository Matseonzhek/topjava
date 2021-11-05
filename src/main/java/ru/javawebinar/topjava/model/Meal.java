package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.MEAL_DELETE, query = "DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:id"),
        @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT m FROM Meal m WHERE m.user.id=:id ORDER BY m.dateTime desc "),
        @NamedQuery(name = Meal.MEAL_ID, query = "SELECT m FROM Meal m WHERE m.id =: id AND m.user.id=:id "),
        @NamedQuery(name = Meal.MEAL_FILTERED, query = "SELECT m FROM Meal m WHERE m.dateTime BETWEEN :startdate AND :enddate " +
                "and m.user.id=:id ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.MEAL_UPDATE, query = "UPDATE Meal m SET m.description=:description, m.calories=:calories," +
                " m.dateTime=:dateTime WHERE m.id=:id AND m.user.id=:userId")
})

@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = { "user_id", "date_time"})})
public class Meal extends AbstractBaseEntity {

    public static final String MEAL_DELETE = "Meal.delete";
    public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String MEAL_FILTERED = "Meal.getFiltered";
    public static final String MEAL_ID = "Meal.getById";
    public static final String MEAL_UPDATE = "Meal.update";

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 50)
    private String description;

    @Column(name = "calories", nullable = false)
    @Range(min = 10, max = 5000)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
