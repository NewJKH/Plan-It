package org.jkh.planit.repository;

import lombok.RequiredArgsConstructor;
import org.jkh.planit.entity.User;
import org.jkh.planit.exception.UserNotFoundException;
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
                .findFirst();
    }

    public Optional<User> findByUsername(String username){
        return jdbcTemplate.query(" "+
                        "SELECT * " +
                        "FROM users " +
                        "WHERE username = ?",userRowMapper(),username).stream()
                .findFirst();
    }

    public User findByUsernameOrThrow(String username) {
        return this.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);}

    public User findByIdOrThrow(int userId) {
        return this.findById(userId)
                .orElseThrow(UserNotFoundException::new);
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
