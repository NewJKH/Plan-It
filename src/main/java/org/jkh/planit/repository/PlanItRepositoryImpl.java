package org.jkh.planit.repository;

import lombok.RequiredArgsConstructor;
import org.jkh.planit.dto.response.PlanItResponse;
import org.jkh.planit.entity.Plan;
import org.jkh.planit.exception.PlanNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
public class PlanItRepositoryImpl implements PlanItRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Plan getPlansByScheduleId(int scheduleId) {
        return this.get(scheduleId)
                .orElseThrow(PlanNotFoundException::new);
    }

    @Override
    public Optional<Plan> get(int schedulerId) {
        return jdbcTemplate.query(" "+
                "SELECT * "+
                "FROM schedules "+
                "WHERE schedule_id = ?",planRowMapper(), schedulerId).stream().findAny();
    }

    @Override
    public void save(Plan plan) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        simpleJdbcInsert.withTableName("schedules").usingGeneratedKeyColumns("schedule_id");

        Map<String, Object> param = new HashMap<>();
        param.put("title",plan.getTitle());
        param.put("content",plan.getContents());
        param.put("user_id",plan.getUserId());
        param.put("created_at",plan.getCreateDate());
        param.put("modified_at",plan.getModifyDate());
        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(param));
        new PlanItResponse(key.intValue(),plan.getUserId(), plan.getTitle(), plan.getContents());
    }

    @Override
    public Page<Plan> findPlansByDate(Timestamp timestamp, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int offset = (int) pageable.getOffset();

        List<Plan> list = jdbcTemplate.query(" "+
                "SELECT * " +
                "FROM schedules " +
                "WHERE created_at > ? " +
                "ORDER BY created_at DESC LIMIT ? OFFSET ?", planRowMapper(),timestamp,pageSize,offset);

        int total = jdbcTemplate.queryForObject(" " +
                "SELECT COUNT(*) " +
                "FROM schedules " +
                "WHERE created_at > ?", Integer.class, timestamp);

        return new PageImpl<>(list,pageable,total);
    }

    @Override
    public Page<Plan> findPlansByUserId(int userId, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int offset = (int) pageable.getOffset();

        List<Plan> list = jdbcTemplate.query(" "+
                "SELECT * " +
                "FROM schedules " +
                "WHERE user_id = ? " +
                "ORDER BY schedule_id LIMIT ? OFFSET ?", planRowMapper(),userId,pageSize,offset);

        int total = jdbcTemplate.queryForObject(" " +
                "SELECT COUNT(*) " +
                "FROM schedules " +
                "WHERE user_id = ?", Integer.class, userId);

        return new PageImpl<>(list,pageable,total);
    }

    @Deprecated
    public Page<Plan> getPlanByUsername(String username, Pageable pageable){
        int pageSize = pageable.getPageSize();
        int offset = (int) pageable.getOffset();

        List<Plan> list = jdbcTemplate.query(" "+
                "SELECT s.* "+
                "FROM schedules s "+
                    "JOIN users u ON s.user_id = u.user_id "+
                "WHERE u.username = ? "+
                "ORDER BY s.schedule_id "+
                "LIMIT ? "+
                "OFFSET ?", planRowMapper(), username, pageSize, offset);

        int total = jdbcTemplate.queryForObject(" "+
                "SELECT COUNT(*) " +
                "FROM schedules s " +
                    "JOIN users u ON s.user_id = u.user_id " +
                "WHERE u.username = ?", Integer.class, username);

        return new PageImpl<>(list,pageable,total);
    }

    @Override
    public int update(Plan plan) {
        return jdbcTemplate.update(" "+
                "UPDATE schedules "+
                "SET content = ?, modified_at = ?"+
                "WHERE schedule_id = ?",plan.getContents(), new Timestamp(System.currentTimeMillis()), plan.getScheduleId());
    }

    @Override
    public int deletePlan(int scheduleId) {
        return jdbcTemplate.update(" "+
                "DELETE FROM schedules "+
                "WHERE schedule_id = ?",scheduleId);

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
}
