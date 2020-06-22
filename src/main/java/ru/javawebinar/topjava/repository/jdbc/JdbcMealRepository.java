package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

@Repository("jdbcMealRepository")
public class JdbcMealRepository implements MealRepository {
    private static final RowMapper<Meal> ROW_MAPPER = createRowMapper();

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    public JdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("dateTime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("userId", userId);
        if (meal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE meals SET date_time=:dateTime, description=:description, calories=:calories " +
                        "WHERE id=:id AND user_id=:userId", map) == 0) {
            return null;
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        String sql = "DELETE FROM meals WHERE id = ? AND user_id = ? ";
        Object[] args = new Object[] {id, userId};
        return jdbcTemplate.update(sql, args) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        String sql = "SELECT * FROM meals WHERE id = ? AND user_id = ? ";
        Object[] args = new Object[] {id, userId};
        return jdbcTemplate.queryForObject(sql, args, ROW_MAPPER);
    }

    @Override
    public List<Meal> getAll(int userId) {
        String sql = "SELECT * FROM meals WHERE user_id = ? ORDER BY date_time DESC";

        return jdbcTemplate.query(sql, ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        String sql = "SELECT * FROM meals " +
                "WHERE user_id = ?  " +
                "AND date_time BETWEEN ? AND ?" +
                "ORDER BY date_time DESC";

        Object[] args = new Object[] {userId, startDate, endDate};
        return jdbcTemplate.query(sql, args, ROW_MAPPER);
    }

    private static RowMapper<Meal> createRowMapper() {
        return (resultSet, i) -> {
            Integer id = resultSet.getInt("id");
            LocalDateTime dateTime = resultSet.getTimestamp("date_time").toLocalDateTime();
            String description = resultSet.getString("description");
            Integer calories = resultSet.getInt("calories");
            return new Meal(id, dateTime, description, calories);
        };
    }


}
