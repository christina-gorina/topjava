package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    private static Integer authUserId;

    public static int authUserId() {
        return 1;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }

    public static int setAuthUserId(int userId) {
        return authUserId = userId;
    }

    public static int getAuthUserId() {
        return authUserId;
    }
}