package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> save(m, m.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> (oldMeal.getUserId() - userId) == 0 ? meal : oldMeal);
    }

    @Override
    public List<Meal> getAll(int userId) {

        List<Meal> userMeals = repository.values().stream()
                .collect(Collectors.groupingBy(Meal::getUserId)).get(userId);

        userMeals.sort(Comparator.comparing(Meal::getDateTime).reversed());
        return userMeals;
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.values().stream()
                .filter(v -> (v.getId() == id) && (v.getUserId() == userId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.remove(id, get(id, userId));
    }

    @Override
    public List<Meal> filterByDay(int userId, LocalDate startDate, LocalDate endDate) {
        return MealsUtil
                .getFilteredByDay(getAll(userId), startDate, endDate);
    }
}





