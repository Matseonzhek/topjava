package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
    private MealService service = appCtx.getBean(MealService.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                Integer.parseInt(request.getParameter("idUser")),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        service.update(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                service.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(SecurityUtil.authUserId(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        service.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                LocalDate startDate;
                LocalDate endDate;
                LocalTime startTime;
                LocalTime endTime;
                String paramStartDate = request.getParameter("startDate");
                String paramEndDate = request.getParameter("endDate");
                String paramStartTme = request.getParameter("startTime");
                String paramEndTime = request.getParameter("endTime");
                if (paramStartDate == null) {
                    startDate = LocalDate.MIN;
                } else {
                    startDate = LocalDate.parse(paramStartDate);
                }
                if (paramEndDate == null) {
                    endDate = LocalDate.MAX;
                } else {
                    endDate = LocalDate.parse(paramEndDate);
                }
                if (paramStartTme == null) {
                    startTime = LocalTime.MIDNIGHT;
                } else {
                    startTime = LocalTime.parse(paramStartTme);
                }
                if (paramEndTime == null) {
                    endTime = LocalTime.NOON;
                } else {
                    endTime = LocalTime.parse(paramEndTime);
                }
                if (paramStartDate != null || paramEndDate != null) {
                    request.setAttribute("meals", MealsUtil.getFilteredTos(service.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY, startTime, endTime));
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                }
                if (paramStartTme != null || paramEndDate != null) {
                    request.setAttribute("meals", MealsUtil.getFilteredByDate(service.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY, startDate, endDate));
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                }
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        MealsUtil.getTos(service.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
//                        MealsUtil.getTos(repository.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private LocalTime getTime(HttpServletRequest request) {
        String paramTime = request.getParameter("time");
        if (paramTime == null) {
            return LocalTime.MIDNIGHT;
        }
        return LocalTime.parse(paramTime);

    }

    @Override
    public void destroy() {
        appCtx.close();
    }
}
