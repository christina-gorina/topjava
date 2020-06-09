package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO {
    public boolean add(Meal meal);

    public boolean delete(int id);

    public boolean update(Meal meal);

    public List<Meal> getAll();

    public Meal getById(int id);

    public boolean isExist(int id);
}
