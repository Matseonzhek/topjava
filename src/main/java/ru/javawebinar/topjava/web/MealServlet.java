package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.SimpleFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static String MEALS = "/meals.jsp";
    private static String EDIT_MEAL = "/editMeal.jsp";
    private static String HOME = "/home.html";
    private final MealDao mealDao;

    public MealServlet() {
        super();
        this.mealDao = new MealDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("forward to meals");

        String forward = "";
        String action = (String) req.getAttribute("action");

        if (action.equalsIgnoreCase("delete")) {
            int idMeal = Integer.parseInt(req.getParameter("idMeal"));
            mealDao.deleteMealRecord(idMeal);
            forward = MEALS;
            req.setAttribute("mealDao", mealDao);
        } else if (action.equalsIgnoreCase("edit")) {
            forward = EDIT_MEAL;
        } else if (action.equalsIgnoreCase("insert")) {
            forward = EDIT_MEAL;
        } else {
            forward = HOME;
        }
        RequestDispatcher view = req.getRequestDispatcher(forward);
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idMeal = Integer.parseInt(req.getParameter("idMeal"));
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        if (idMeal == 0) {
            Meal meal = new Meal(dateTime, description, calories);
            mealDao.createMealRecord(meal);
        }
        if (idMeal != 0) {
            mealDao.updateMealRecord(idMeal, dateTime, description, calories);
        }
    }
}
