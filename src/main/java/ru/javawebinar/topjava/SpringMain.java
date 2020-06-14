package ru.javawebinar.topjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SpringMain {
    private static final Logger log = LoggerFactory.getLogger(SpringMain.class);

    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "Mona", "mona@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "Alex", "alex@mail.ru", "password", Role.USER));
            adminUserController.create(new User(null, "Alex", "alexadmin@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "Zef", "zef@mail.ru", "password", Role.USER));
            adminUserController.create(new User(null, "Brien", "brien@mail.ru", "password", Role.USER));
            adminUserController.create(new User(null, "Tim", "tim@mail.ru", "password", Role.ADMIN));

            User userByEmail = adminUserController.getByMail("Alex@mail.ru");
            List<User> users = adminUserController.getAll();
            log.info("users {}", users);


            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            Collection<Meal> meals = mealRestController.getAllByUserId(SecurityUtil.authUserId());
            log.info("meals {}", meals);

        }
    }
}
