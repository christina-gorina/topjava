package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    public void deleteByUserId(int id, int userId) {
        checkNotFoundWithId(repository.deleteByUserId(id, userId), id);
    }

    public Meal getByUserId(int id, int userId) {
        return checkNotFoundWithId(repository.getByUserId(id, userId), id);
    }

    public Collection<Meal> getAllByUserId(int userId) {
        return repository.getAllByUserId(userId);
    }
}