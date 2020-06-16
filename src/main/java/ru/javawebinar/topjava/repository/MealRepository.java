package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository {
    // null if not found, when updated
    Meal save(Meal meal, int userId);

    List<Meal> getAll(int userId);

    Meal get(int id, int userId);

    boolean delete(int id, int userId);

    List<Meal> filterByDay(int userId, LocalDate startDate, LocalDate endDate);
}
