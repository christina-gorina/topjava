DELETE
FROM meals;
DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES (make_timestamp(2020, 6, 19, 8, 15, 0), 'Завтрак пользователя 1', 600, 100000),
       (make_timestamp(2020, 6, 19, 15, 20, 0), 'Обед пользователя 1', 700, 100000),
       (make_timestamp(2020, 6, 19, 21, 10, 0), 'Ужин пользователя 1', 300, 100000),
       (make_timestamp(2020, 6, 20, 9, 0, 0), 'Завтрак пользователя 2', 500, 100000),
       (make_timestamp(2020, 6, 20, 16, 0, 0), 'Обед пользователя 2', 1600, 100000),
       (make_timestamp(2020, 6, 20, 22, 0, 0), 'Ужин пользователя 2', 400, 100000),
       (make_timestamp(2020, 6, 22, 9, 20, 0), 'Завтрак админа', 300, 100001),
       (make_timestamp(2020, 6, 22, 12, 12, 0), 'Перекус админа', 400, 100001),
       (make_timestamp(2020, 6, 22, 16, 13, 0), 'Обед админа', 800, 100001),
       (make_timestamp(2020, 6, 22, 22, 30, 0), 'Ужин админа', 500, 100001);
