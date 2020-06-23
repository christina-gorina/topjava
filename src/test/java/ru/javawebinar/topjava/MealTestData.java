package ru.javawebinar.topjava;

import org.assertj.core.api.Assertions;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int NOT_FOUND = 10;
    public static final int USER_ID = 100000;
    public static final int ADMIN_ID = 100001;

    public static final int ID_USER_MEAL_1 = 100002;
    public static final int ID_USER_MEAL_2 = 100003;
    public static final int ID_USER_MEAL_3 = 100004;
    public static final int ID_USER_MEAL_4 = 100005;
    public static final int ID_USER_MEAL_5 = 100006;
    public static final int ID_USER_MEAL_6 = 100007;

    public static final int ID_ADMIN_MEAL_1 = 100008;
    public static final int ID_ADMIN_MEAL_2 = 100009;
    public static final int ID_ADMIN_MEAL_3 = 100010;
    public static final int ID_ADMIN_MEAL_4 = 100011;

    public static final LocalDate START_DATE = LocalDate.of(2020, Month.JUNE, 19);
    public static final LocalDate END_DATE = LocalDate.of(2020, Month.JUNE, 19);

    public static final Meal USER_MEAL_1 = new Meal(ID_USER_MEAL_1, LocalDateTime.of(2020, Month.JUNE, 19, 8, 15), "Завтрак пользователя 1", 600);
    public static final Meal USER_MEAL_2 = new Meal(ID_USER_MEAL_2, LocalDateTime.of(2020, Month.JUNE, 19, 15, 20), "Обед пользователя 1", 700);
    public static final Meal USER_MEAL_3 = new Meal(ID_USER_MEAL_3, LocalDateTime.of(2020, Month.JUNE, 19, 21, 10), "Ужин пользователя 1", 300);
    public static final Meal USER_MEAL_4 = new Meal(ID_USER_MEAL_4, LocalDateTime.of(2020, Month.JUNE, 20, 9, 0), "Завтрак пользователя 2", 500);
    public static final Meal USER_MEAL_5 = new Meal(ID_USER_MEAL_5, LocalDateTime.of(2020, Month.JUNE, 20, 16, 0), "Обед пользователя 2", 1600);
    public static final Meal USER_MEAL_6 = new Meal(ID_USER_MEAL_6, LocalDateTime.of(2020, Month.JUNE, 20, 22, 0), "Ужин пользователя 2", 400);
    public static final Meal ADMIN_MEAL_1 = new Meal(ID_ADMIN_MEAL_1, LocalDateTime.of(2020, Month.JUNE, 22, 9, 20), "Завтрак админа", 300);
    public static final Meal ADMIN_MEAL_2 = new Meal(ID_ADMIN_MEAL_2, LocalDateTime.of(2020, Month.JUNE, 22, 12, 12), "Перекус админа", 400);
    public static final Meal ADMIN_MEAL_3 = new Meal(ID_ADMIN_MEAL_3, LocalDateTime.of(2020, Month.JUNE, 22, 16, 13), "Обед админа", 800);
    public static final Meal ADMIN_MEAL_4 = new Meal(ID_ADMIN_MEAL_4, LocalDateTime.of(2020, Month.JUNE, 22, 22, 30), "Ужин админа", 500);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JUNE, 1, 8, 15), "Завтрак пользователя в другой день", 600);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(USER_MEAL_1);
        updated.setDescription("UpdatedDescription");
        updated.setCalories(1000);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        Assertions.assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(List<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparator((m1, m2) -> {
            if (m1.getId().equals(m2.getId()) &&
                    m1.getDateTime().equals(m2.getDateTime()) &&
                    m1.getDescription().equals(m2.getDescription()) &&
                    m1.getCalories().equals(m2.getCalories())) {
                return 0;
            } else {
                return 1;
            }
        }).isEqualTo(expected);
    }
}
