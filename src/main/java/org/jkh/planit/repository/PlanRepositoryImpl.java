package org.jkh.planit.repository;

import org.jkh.planit.domain.Plan;
import org.jkh.planit.dto.response.PlanResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PlanRepositoryImpl implements PlanItRepository {
    private final JdbcTemplate jdbcTemplate;

    public PlanRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public PlanResponse save(Plan plan) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        simpleJdbcInsert.withTableName("schedules").usingGeneratedKeyColumns("schedule_id");

        Map<String, Object> param = new HashMap<>();
        param.put("title",plan.getTitle());
        param.put("content",plan.getContents());
        param.put("user_id",plan.getUserId());
        param.put("create_at",plan.getCreateDate());
        param.put("modified_at",plan.getModifyDate());
        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(param));

        return new PlanResponse(key.intValue(),plan.getUserId(), plan.getTitle(), plan.getContents());
    }
}
