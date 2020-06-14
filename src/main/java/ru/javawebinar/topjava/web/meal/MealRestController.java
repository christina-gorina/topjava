package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal);
    }

    public void deleteByUserId(int id, int userId) {
        log.info("deleteByUserId {}", id);
        service.deleteByUserId(id, userId);
    }

    public Meal getByUserId(int id, int userId) {
        log.info("get {}", id);
        return service.getByUserId(id, userId);
    }

    public Collection<Meal> getAllByUserId(int userId) {
        log.info("getAllByUserId");
        return service.getAllByUserId(userId);
    }
}