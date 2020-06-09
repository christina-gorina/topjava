package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoInMemory implements MealDao {
    private final AtomicInteger ID = new AtomicInteger(1);
    private final Map<Integer, Meal> mealsToStorage = new ConcurrentHashMap<>();

    @Override
    public Meal add(Meal meal) {
        meal.setId(ID.getAndIncrement());
        mealsToStorage.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id) {
        mealsToStorage.remove(id);
        return true;
    }

    @Override
    public Meal update(Meal meal) {
        mealsToStorage.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealsToStorage.values());
    }

    @Override
    public Meal getById(int id) {
        return mealsToStorage.get(id);
    }
}
