package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(String id, String dateTime, String description, String calories) {
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(dateTime),
                description,
                Integer.parseInt(calories),
                SecurityUtil.getAuthUserId()
        );

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        log.info("create {}", meal);
        return service.create(meal, SecurityUtil.getAuthUserId());
    }

    public Meal update(String action, String id) {
        log.info("update");
        Meal meal = new Meal(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                "",
                1000,
                SecurityUtil.getAuthUserId()
        );
        return service.update(action, meal, id, SecurityUtil.getAuthUserId());
    }

    public void delete(int id) {
        log.info("deleteByUserId {}", id);
        service.delete(id, SecurityUtil.getAuthUserId());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.getAuthUserId());
    }

    public List<MealTo> getMealTo() {
        log.info("getAllByUserId");

        List<Meal> meals = service.getAll(SecurityUtil.getAuthUserId());
        return MealsUtil.getTos(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> filterByDayTime(String startDateParam, String endDateParam, String startTimeParam, String endTimeParam) {
        log.info("filter");
        LocalDate startDate = startDateParam.isEmpty() ? LocalDate.MIN : LocalDate.parse(startDateParam);
        LocalDate endDate = endDateParam.isEmpty() ? LocalDate.MAX : LocalDate.parse(endDateParam).plusDays(1);
        LocalTime startTime = startTimeParam.isEmpty() ? LocalTime.MIN : LocalTime.parse(startTimeParam);
        LocalTime endTime = endTimeParam.isEmpty() ? LocalTime.MAX : LocalTime.parse(endTimeParam);

        List<Meal> mealsFilteredByDay = service.filterByDay(SecurityUtil.getAuthUserId(), startDate, endDate);

        return MealsUtil
                .getFilteredTos(mealsFilteredByDay, MealsUtil.DEFAULT_CALORIES_PER_DAY, startTime, endTime);
    }
}