package ru.geekbrains.security.dtos;

import lombok.Data;
import ru.geekbrains.security.entities.User;

@Data
public class UserDto {
    private String name;
    private String email;
    private long score;

    public UserDto(User user) {
        name = user.getUsername();
        email = user.getEmail();
        score = user.getScore();
    }

}