package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDAOImplImMemory implements MealDAO {
    private static final AtomicInteger ID = new AtomicInteger(1);
    private static final Map<Integer, Meal> mealsToStorage = new ConcurrentHashMap<>();

    @Override
    public boolean add(Meal meal) {
        if(isExist(meal.getId()))
            return false;

        meal.setId(ID.getAndIncrement());
        mealsToStorage.put(meal.getId(), meal);
        return true;
    }

    @Override
    public boolean delete(int id) {
        if(!isExist(id))
            return false;

        mealsToStorage.remove(id);
        return true;
    }

    @Override
    public boolean update(Meal meal) {
        if(!isExist(meal.getId()))
            return false;

        mealsToStorage.put(meal.getId(), meal);
        return true;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealsToStorage.values());
    }

    @Override
    public Meal getById(int id) {
        return mealsToStorage.get(id);
    }

    @Override
    public boolean isExist(int id) {
        return mealsToStorage.containsKey(id);
    }
}
