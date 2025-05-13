package org.jkh.planit.repository;

import lombok.RequiredArgsConstructor;
import org.jkh.planit.dto.request.PlanItUpdateRequest;
import org.jkh.planit.dto.response.PlanItResponse;
import org.jkh.planit.entity.Plan;
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
    public Optional<Plan> get(int schedulerId) {
        return jdbcTemplate.query(" "+
                "SELECT * "+
                "FROM schedules "+
                "WHERE schedule_id = ?",planRowMapper(), schedulerId).stream().findAny();
    }

    @Override
    public PlanItResponse save(Plan plan) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        simpleJdbcInsert.withTableName("schedules").usingGeneratedKeyColumns("schedule_id");

        Map<String, Object> param = new HashMap<>();
        param.put("title",plan.getTitle());
        param.put("content",plan.getContents());
        param.put("user_id",plan.getUserId());
        param.put("created_at",plan.getCreateDate());
        param.put("modified_at",plan.getModifyDate());
        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(param));

        return new PlanItResponse(key.intValue(),plan.getUserId(), plan.getTitle(), plan.getContents());
    }

    @Override
    public List<PlanItResponse> getPlansByDate(Timestamp timestamp) {
        return jdbcTemplate.query(" "+
                "SELECT * " +
                "FROM schedules " +
                "WHERE created_at > ?", planResponseRowMapper(),timestamp);
    }

    @Override
    public Page<PlanItResponse> getPlansByDate(Timestamp timestamp, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int offset = (int) pageable.getOffset();

        List<PlanItResponse> list = jdbcTemplate.query(" "+
                "SELECT * " +
                "FROM schedules " +
                "WHERE created_at > ? " +
                "ORDER BY created_at DESC LIMIT ? OFFSET ?", planResponseRowMapper(),timestamp,pageSize,offset);

        int total = jdbcTemplate.queryForObject(" " +
                "SELECT COUNT(*) " +
                "FROM schedules " +
                "WHERE created_at > ?", Integer.class, timestamp);

        return new PageImpl<>(list,pageable,total);
    }

    @Override
    public List<PlanItResponse> getPlansByUserId(int userId) {
        return jdbcTemplate.query(" "+
                "SELECT * " +
                "FROM schedules " +
                "WHERE user_id = ?", planResponseRowMapper(),userId);
    }

    @Override
    public Page<PlanItResponse> getPlansByUserId(int userId, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int offset = (int) pageable.getOffset();

        List<PlanItResponse> list = jdbcTemplate.query(" "+
                "SELECT * " +
                "FROM schedules " +
                "WHERE user_id = ? " +
                "ORDER BY schedule_id LIMIT ? OFFSET ?", planResponseRowMapper(),userId,pageSize,offset);

        int total = jdbcTemplate.queryForObject(" " +
                "SELECT COUNT(*) " +
                "FROM schedules " +
                "WHERE user_id = ?", Integer.class, userId);

        return new PageImpl<>(list,pageable,total);
    }

    public Page<PlanItResponse> getPlanByUsername(String username, Pageable pageable){
        int pageSize = pageable.getPageSize();
        int offset = (int) pageable.getOffset();

        List<PlanItResponse> list = jdbcTemplate.query(" "+
                "SELECT s.* "+
                "FROM schedules s "+
                    "JOIN users u ON s.user_id = u.user_id "+
                "WHERE u.username = ? "+
                "ORDER BY s.schedule_id "+
                "LIMIT ? "+
                "OFFSET ?", planResponseRowMapper(), username, pageSize, offset);

        int total = jdbcTemplate.queryForObject(" "+
                "SELECT COUNT(*) " +
                "FROM schedules s " +
                    "JOIN users u ON s.user_id = u.user_id " +
                "WHERE u.username = ?", Integer.class, username);

        return new PageImpl<>(list,pageable,total);
    }

    @Override
    public int update(PlanItUpdateRequest plan) {
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

    private RowMapper<PlanItResponse> planResponseRowMapper(){
        return (rs, rowNum) -> new PlanItResponse(
                rs.getInt("schedule_id"),
                rs.getInt("user_id"),
                rs.getString("title"),
                rs.getString("content")
        );
    }
}
