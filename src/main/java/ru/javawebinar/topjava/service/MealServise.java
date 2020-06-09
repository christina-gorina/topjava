package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealServise {
    Meal add(Meal meal);

    boolean delete(int id);

    Meal update(Meal meal);

    List<Meal> getAll();

    Meal getById(int id);
}