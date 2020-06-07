package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import java.time.LocalTime;
import java.util.List;

public class MealsUtil {
    public static void main(String[] args) {
        List<Meal> meals = MealsBuilder.createMeals();
        List<MealTo> mealsTo = MealsBuilder.filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
    }
}