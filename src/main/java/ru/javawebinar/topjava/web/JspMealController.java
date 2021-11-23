package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {

    @Autowired
    MealRestController mealController;

    @GetMapping
    public String getMealList(HttpServletRequest request) {
        request.setAttribute("meals", mealController.getAll());
        return "meals";
    }

    @GetMapping("/delete/{id}")
    public String deleteMeal(@PathVariable("id") String id, HttpServletRequest request) {
        String paramId = Objects.requireNonNull(id);
        int idMeal = Integer.parseInt(paramId);
        mealController.delete(idMeal);
        request.setAttribute("meals", mealController.getAll());
        return "meals";
    }

    @GetMapping("/update/{id}")
    public String updateMeal(@PathVariable("id") String id, Model model) {
        String paramId = Objects.requireNonNull(id);
        int idMeal = Integer.parseInt(paramId);
        Meal meal = mealController.get(idMeal);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/create")
    public String create(HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        request.setAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/update")
    public String saveMeal(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("windows-1252");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.hasLength(request.getParameter("id"))) {
            mealController.update(meal, getId(request));
        } else {
            mealController.create(meal);
        }
        request.setAttribute("meals", mealController.getAll());
        return "meals";
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @GetMapping("/filer")
    public String filter(HttpServletRequest request,
                         @RequestParam(name = "startDate", required = false) LocalDate startDate,
                         @RequestParam(name = "startTime", required = false) LocalTime startTime,
                         @RequestParam(name = "endDate", required = false) LocalDate endDate,
                         @RequestParam(name = "endTime", required = false) LocalTime endTime) {
        request.setAttribute("meals", mealController.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
