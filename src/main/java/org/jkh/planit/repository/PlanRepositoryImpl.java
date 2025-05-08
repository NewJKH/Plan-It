package org.jkh.planit.repository;

import lombok.RequiredArgsConstructor;
import org.jkh.planit.domain.Plan;
import org.jkh.planit.dto.request.PlanRequest;
import org.jkh.planit.dto.response.PlanResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlanRepositoryImpl implements PlanItRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Plan> get(int schedulerId) {
        return jdbcTemplate.query(" "+
                "SELECT * "+
                "FROM schedules "+
                "WHERE schedule_id = ?",planRowMapper(), schedulerId).stream().findAny();
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

    @Override
    public List<PlanResponse> getPlansByDate(Timestamp timestamp) {
        return jdbcTemplate.query(" "+
                "SELECT * " +
                "FROM schedules " +
                "WHERE created_at > ?", planResponseRowMapper(),timestamp);
    }

    @Override
    public List<PlanResponse> getPlansByUserId(int userId) {
        return jdbcTemplate.query(" "+
                "SELECT * " +
                "FROM schedules " +
                "WHERE user_id = ?", planResponseRowMapper(),userId);
    }

    @Override
    public int update(PlanRequest plan) {
        return jdbcTemplate.update(" "+
                "UPDATE schedules "+
                "SET content = ?, modified_at = ?"+
                "WHERE schedule_id = ?",plan.getContents(), new Timestamp(System.currentTimeMillis()),plan.getScheduleId());
    }

    private RowMapper<Plan> planRowMapper(){
        return (rs, rowNum) -> new Plan(
                rs.getInt("schedule_id"),
                rs.getInt("user_id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("modified_at")
        );
    }

    private RowMapper<PlanResponse> planResponseRowMapper(){
        return (rs, rowNum) -> new PlanResponse(
                rs.getInt("schedule_id"),
                rs.getInt("user_id"),
                rs.getString("title"),
                rs.getString("content")
        );
    }
}
