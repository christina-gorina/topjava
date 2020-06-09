package ru.javawebinar.topjava.controller;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.MealDAOImplImMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

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

public class MealController extends HttpServlet {
    private final MealDAOImplImMemory mealDAO;
    private static final Logger log = getLogger(MealController.class);
    private static final DateTimeFormatter formatter = TimeUtil.getDateTimeFormatter();

    public MealController() {
        super();
        mealDAO = new MealDAOImplImMemory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("MealController doPost");
        request.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, calories);

        if (idParam == null || idParam.isEmpty()) {
            mealDAO.add(meal);
        } else {
            meal.setId(Integer.parseInt(idParam));
            mealDAO.update(meal);
        }

        String path = request.getContextPath() + "/meals";
        response.sendRedirect(path);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("MealController doGet");
        String action = request.getParameter("action");
        action = (action == null) ? "info" : action;

        if (action.equalsIgnoreCase("insert")) {
            request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("delete")) {
            mealDAO.delete(Integer.parseInt(request.getParameter("id")));
            String path = request.getContextPath() + "/meals";
            response.sendRedirect(path);
        } else if (action.equalsIgnoreCase("edit")) {
            Meal meal = mealDAO.getById(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
        } else {
            final int caloriesPerDay = 2000;
            List<MealTo> mealsTo = MealsUtil.filteredByStreams(mealDAO.getAll(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
            request.setAttribute("formatter", formatter);
            request.setAttribute("mealsTo", mealsTo);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }
}
