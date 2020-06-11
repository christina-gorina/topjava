package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.DAO.MealDao;
import ru.javawebinar.topjava.DAO.MealDaoInMemory;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class MealServiseImpl implements MealServise {
    private MealDao mealDAO = new MealDaoInMemory();

    @Override
    public Meal add(Meal meal) {
        mealDAO.add(meal);
        return meal;
    }

    @Override
    public boolean delete(int id) {
        mealDAO.delete(id);
        return true;
    }

    @Override
    public Meal update(Meal meal) {
        mealDAO.update(meal);
        return meal;
    }

    @Override
    public List<Meal> getAll() {
        return mealDAO.getAll();
    }

    @Override
    public Meal getById(int id) {
        return mealDAO.getById(id);
    }
}
