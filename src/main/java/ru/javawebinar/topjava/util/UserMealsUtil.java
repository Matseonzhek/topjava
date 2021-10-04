package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.*;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> userMealWithExcessesList = new ArrayList<>();
        //sum up meal eaten a day
        Map<LocalDate,Integer> mapActualCaloriesPerDay = new HashMap<>();
        for (UserMeal userMeal: meals) {
            LocalDate calculatedDate = convertLocalDateToDate(userMeal.getDateTime());
            Integer caloriesPerMeal = userMeal.getCalories();
            mapActualCaloriesPerDay.putIfAbsent(calculatedDate,0);
            mapActualCaloriesPerDay.merge(calculatedDate,caloriesPerMeal,Integer::sum);
        }

        //fill up TOTAL list of UserMealsWithExcess
        for (UserMeal userMeal: meals) {
            LocalDate actualDate = convertLocalDateToDate(userMeal.getDateTime());
            Integer actualCaloriesPerDay = mapActualCaloriesPerDay.get(actualDate);
            if(actualCaloriesPerDay > caloriesPerDay){
                UserMealWithExcess userMealWithExcess = new UserMealWithExcess(userMeal.getDateTime(),userMeal.getDescription(),userMeal.getCalories(),true);
                userMealWithExcessesList.add(userMealWithExcess);
            }
            if(actualCaloriesPerDay <= caloriesPerDay){
                UserMealWithExcess userMealWithExcess = new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),false);
                userMealWithExcessesList.add(userMealWithExcess);
            }
        }

        //select meals according condition - between @startTime and @endTime
        List<UserMealWithExcess> resultList = new ArrayList<>();
        for (UserMealWithExcess userMealWithExcess:userMealWithExcessesList) {
            LocalTime actualTime = convertLocalDateToTime(userMealWithExcess.getDateTime());
            if(TimeUtil.isBetweenHalfOpen(actualTime,startTime,endTime)){
                resultList.add(userMealWithExcess);
            }
        }

        return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }

    public static LocalDate convertLocalDateToDate(LocalDateTime localDateTime) {
//        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return localDateTime.toLocalDate();
    }

    public static LocalTime convertLocalDateToTime (LocalDateTime localDateTime){
        return localDateTime.toLocalTime();
    }

}
