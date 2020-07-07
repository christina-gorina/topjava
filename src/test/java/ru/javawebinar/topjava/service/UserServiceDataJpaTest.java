package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;


import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles("datajpa")
public class UserServiceDataJpaTest extends UserServiceTest {

    @Test
    public void getWithMeal() throws Exception {
        User user = service.getWithMeal(ADMIN_ID);
        USER_MATCHER.assertMatch(user, getAdminWithMeals());
    }
}
