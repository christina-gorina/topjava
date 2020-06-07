package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealTo {
    private final LocalDateTime mDateTime;

    private final String mDescription;

    private final int mCalories;

    private final boolean mExcess;

    public MealTo(LocalDateTime dateTime, String description, int calories, boolean excess) {
        mDateTime = dateTime;
        mDescription = description;
        mCalories = calories;
        mExcess = excess;
    }

    public LocalDateTime getDateTime() {
        return mDateTime;
    }

    public String getDateTimeFormatted() {
        return mDateTime.format(DateTimeFormatter.ofPattern("d MMM uuuu HH mm"));
    }

    public String getDescription() {
        return mDescription;
    }

    public int getCalories() {
        return mCalories;
    }

    public boolean isExcess() {
        return mExcess;
    }


    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + mDateTime +
                ", description='" + mDescription + '\'' +
                ", calories=" + mCalories +
                ", excess=" + mExcess +
                '}';
    }
}
