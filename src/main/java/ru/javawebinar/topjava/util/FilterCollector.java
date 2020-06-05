package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMealWithExcess;
import java.time.LocalTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FilterCollector implements Collector<UserMealWithExcess, Map.Entry<HashMap<Integer, Integer>, List<UserMealWithExcess>>, List<UserMealWithExcess>> {
    LocalTime mStartTime;
    LocalTime mEndTime;
    int mCaloriesPerDay;

    public FilterCollector(LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        mStartTime = startTime;
        mEndTime = endTime;
        mCaloriesPerDay = caloriesPerDay;
    }

    @Override
    public Supplier<Map.Entry<HashMap<Integer, Integer>, List<UserMealWithExcess>>> supplier() {
        return () -> new AbstractMap.SimpleImmutableEntry<>(new HashMap<>(), new ArrayList<UserMealWithExcess>());
    }

    @Override
    public BiConsumer<Map.Entry<HashMap<Integer, Integer>, List<UserMealWithExcess>>, UserMealWithExcess> accumulator() {

        return (collection, meal) -> {
            int day = meal.getDateTime().toLocalDate().getDayOfMonth();
            collection.getKey().merge(day, meal.getCalories(), Integer::sum);
            collection.getValue().add(meal);
        };

    }

    @Override
    public BinaryOperator<Map.Entry<HashMap<Integer, Integer>, List<UserMealWithExcess>>> combiner() {
        return (c1, c2) -> {
            c1.getValue().addAll(c2.getValue());
            c2.getKey().forEach((key, value) -> c1.getKey().put(key, value));
            return c1;
        };
    }

    @Override
    public Function<Map.Entry<HashMap<Integer, Integer>, List<UserMealWithExcess>>, List<UserMealWithExcess>> finisher() {
        return collection -> {
            Map<Integer, Integer> caloriesMap = collection.getKey();

            return collection.getValue().stream().filter(meal -> {
                int day = meal.getDateTime().toLocalDate().getDayOfMonth();
                return caloriesMap.get(day) <= mCaloriesPerDay;
            }).filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), mStartTime, mEndTime)).collect(Collectors.toList());
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.CONCURRENT);
    }
}
