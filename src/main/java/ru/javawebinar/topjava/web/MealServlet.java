package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealServise;
import ru.javawebinar.topjava.service.MealServiseImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private MealServise mealServise;
    private static Logger log;
    private static DateTimeFormatter formatter;

    @Override
    public void init(ServletConfig config) {
        log = getLogger(MealServlet.class);
        mealServise = new MealServiseImpl();
        formatter = DateTimeFormatter.ofPattern("uuuu-MM-d HH:mm");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("MealServlet: doPost");
        request.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, calories);

        if (idParam == null || idParam.isEmpty()) {
            log.debug("MealServlet: idParam is null or is empty");
            mealServise.add(meal);
        } else {
            log.debug("MealServlet: idParam exist");
            meal.setId(Integer.parseInt(idParam));
            mealServise.update(meal);
        }

        String path = request.getContextPath() + "/meals";
        response.sendRedirect(path);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("MealServlet: doGet");
        String action = request.getParameter("action");
        action = (action == null) ? "info" : action;

        switch (action.toLowerCase()) {
            case "insert":
                log.debug("MealServlet: insert");
                request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
                break;
            case "delete":
                log.debug("MealServlet: delete");
                mealServise.delete(Integer.parseInt(request.getParameter("id")));
                String path = request.getContextPath() + "/meals";
                response.sendRedirect(path);
                break;
            case "edit":
                log.debug("MealServlet: edit");
                Meal meal = mealServise.getById(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
                break;
            default:
                log.debug("MealServlet: default");
                final int caloriesPerDay = 2000;
                List<MealTo> mealsTo = MealsUtil.filteredByStreams(mealServise.getAll(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
                request.setAttribute("formatter", formatter);
                request.setAttribute("mealsTo", mealsTo);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }
}
