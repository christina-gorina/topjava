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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Profile(Profiles.HSQL_DB)
@Repository
public class JdbcMealHsqldbRepository extends JdbcMealRepository implements MealRepository {

    @Autowired
    public JdbcMealHsqldbRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public  Map<String, Date> prepareDate(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Date startDate = Timestamp.valueOf(startDateTime);
        Date endDate = Timestamp.valueOf(endDateTime);

        Map<String, Date> map = new HashMap<>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return map;
    }

    @Override
    public MapSqlParameterSource changeMap(MapSqlParameterSource map, Meal meal) {
        return  map.addValue("date_time", Timestamp.valueOf(meal.getDateTime()));
    }

}
