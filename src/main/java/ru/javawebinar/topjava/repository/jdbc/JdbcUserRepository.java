package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    Set<ConstraintViolation<User>> violations = validator.validate(new User());

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        System.out.println("----violations------");
        System.out.println(violations);

    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        List<Role> roles = new ArrayList<>(user.getRoles());
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            int userId = newKey.intValue();
            user.setId(userId);
            saveRoles(userId, roles);
        }else {
            int userId = user.getId();
            if (namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
                return null;
            }else{
                jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", userId);
                saveRoles(userId, roles);
            }
        }
        return user;
    }

    public int[] saveRoles(int userId, List<Role> roles) {

        return this.jdbcTemplate.batchUpdate(
            "insert into user_roles (role, user_id) values(?, ?)",
            new BatchPreparedStatementSetter() {
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, roles.get(i).toString());
                    ps.setInt(2, userId);
                }

                public int getBatchSize() {
                    return roles.size();
                }
            });
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", id);
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        List<Role> roles = jdbcTemplate.queryForList("SELECT user_roles.role FROM user_roles WHERE user_id=?", Role.class, id);
        users.forEach(u -> u.setRoles(roles));
        User user = DataAccessUtils.singleResult(users);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        User user = DataAccessUtils.singleResult(users);
        List<Role> roles = jdbcTemplate.queryForList("SELECT user_roles.role FROM user_roles WHERE user_id=?", Role.class, user.getId());
        user.setRoles(roles);
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> prepareUsers;
        List<User> users = new ArrayList<>();

        prepareUsers = jdbcTemplate.query("SELECT users.id, users.email, users.name, users.enabled, users.calories_per_day, ur.role FROM users INNER JOIN user_roles ur on users.id = ur.user_id ORDER BY name, email", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                List<Role> userRoles = new ArrayList<>();
                user.setId(resultSet.getInt(1));
                user.setEmail(resultSet.getString(2));
                user.setName(resultSet.getString(3));
                user.setEnabled(resultSet.getBoolean(4));
                user.setCaloriesPerDay(resultSet.getInt(5));
                String role= resultSet.getString(6);
                for (Role r : Role.values()) {
                    if(r.toString().equals(role)){
                        userRoles.add(r);
                    }
                }
                user.setRoles(userRoles);
                return user;
            }
        });

        Map<Integer, List<User>> prepareUsersList = prepareUsers.stream().collect(
                Collectors.groupingBy(User::getId));



        for(Map.Entry<Integer, List<User>> item : prepareUsersList.entrySet()){
            Integer id = item.getKey();
            String name = item.getValue().get(0).getName();
            String email = item.getValue().get(0).getEmail();
            String password = item.getValue().get(0).getPassword();
            Set<Role> roles = new HashSet<>();

            for(User user : item.getValue()){
                for(Role role : user.getRoles()) {
                    roles.add(role);
                }
            }

            User user = new User(id, name, email, password, Role.USER);

            user.setRoles(roles);
            users.add(user);

        }

        return List.copyOf(users);
    }
}

