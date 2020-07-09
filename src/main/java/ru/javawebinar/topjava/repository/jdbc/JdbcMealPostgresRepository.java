package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Profile(Profiles.POSTGRES_DB)
@Repository
public class JdbcMealPostgresRepository  extends JdbcMealRepository implements MealRepository {

    @Autowired
    public JdbcMealPostgresRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Map<String, LocalDateTime> prepareDate(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Map<String, LocalDateTime> map = new HashMap<>();
        map.put("startDate", startDateTime);
        map.put("endDate", endDateTime);
        return map;
    }

    @Override
    public MapSqlParameterSource changeMap(MapSqlParameterSource map, Meal meal) {
        return  map.addValue("date_time", meal.getDateTime());
    }
}