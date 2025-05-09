package org.jkh.planit.repository;

import lombok.RequiredArgsConstructor;
import org.jkh.planit.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<User> findById(int userId){
        return jdbcTemplate.query(" "+
                        "SELECT * " +
                        "FROM users " +
                        "WHERE user_id = ?",userRowMapper(),userId).stream()
                .findAny();
    }

    public Optional<User> findByUsername(String username){
        return jdbcTemplate.query(" "+
                        "SELECT * " +
                        "FROM users " +
                        "WHERE username = ?",userRowMapper(),username).stream()
                .findAny();
    }

    private RowMapper<User> userRowMapper(){
        return (rs, rowNum) -> new User(
                rs.getInt("user_id"),
                rs.getString("user_pw_hash"),
                rs.getString("username"),
                rs.getTimestamp("created_at"),
                rs.getString("email")
        );
    }
}
