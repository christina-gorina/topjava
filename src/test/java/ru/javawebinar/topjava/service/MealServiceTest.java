package ru.javawebinar.topjava.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest extends TestCase {

    @Autowired
    MealService service;

    @Autowired
    private MealRepository repository;

    @Test
    public void create() {
        int userId = USER_ID;
        Meal newMeal = getNew();
        Meal createdMeal = service.create(newMeal, userId);
        Integer newId = createdMeal.getId();
        newMeal.setId(newId);

        assertMatch(createdMeal, newMeal);
        assertMatch(service.get(newId, userId), newMeal);
    }

    @Test
    public void get() {
        Meal meal = service.get(ID_USER_MEAL_1, USER_ID);

        assertMatch(meal, USER_MEAL_1);
    }

    @Test
    public void getAlienMeal() {
        assertThrows(NotFoundException.class, () -> service.get(ID_ADMIN_MEAL_1, USER_ID));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(START_DATE, END_DATE, USER_ID);
        assertMatch(meals, USER_MEAL_3, USER_MEAL_2, USER_MEAL_1);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, ADMIN_MEAL_4, ADMIN_MEAL_3, ADMIN_MEAL_2, ADMIN_MEAL_1);
    }

    @Test
    public void delete() {
        service.delete(ID_USER_MEAL_4, USER_ID);
        assertNull(repository.get(ID_USER_MEAL_4, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deletedAlienMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(ID_USER_MEAL_5, ADMIN_ID));
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(updated.getId(), USER_ID), updated);
    }

    @Test
    public void updateAlienMeal() {
        Meal updated = getUpdated();
        updated.setId(ID_ADMIN_MEAL_1);
        assertThrows(NotFoundException.class, () -> service.update(updated, USER_ID));
    }
}