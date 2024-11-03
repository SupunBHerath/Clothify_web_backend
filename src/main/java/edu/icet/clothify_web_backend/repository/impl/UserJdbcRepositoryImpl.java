package edu.icet.clothify_web_backend.repository.impl;
import edu.icet.clothify_web_backend.entity.UserEntity;
import edu.icet.clothify_web_backend.exception.impl.NotFoundException;
import edu.icet.clothify_web_backend.repository.UserJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@RequiredArgsConstructor
@Repository
public class UserJdbcRepositoryImpl implements UserJdbcRepository {

    private  final JdbcTemplate jdbcTemplate;

    public boolean userExistsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public UserEntity login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<UserEntity> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserEntity.class), email);
        if (userList.isEmpty()) {
            throw new NotFoundException("User not found with email: " + email);
        }

        UserEntity userEntity = userList.get(0);
        if (!userEntity.getPassword().equalsIgnoreCase(password)) throw new NotFoundException("Password  is wrong");


        return userEntity;
    }

}
