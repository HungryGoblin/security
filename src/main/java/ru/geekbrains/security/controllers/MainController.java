package ru.geekbrains.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.security.dtos.UserDto;
import ru.geekbrains.security.entities.User;
import ru.geekbrains.security.services.SecurityService;
import ru.geekbrains.security.services.UserService;

import java.security.Principal;
import java.util.Optional;

@RestController
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

//GET .../app/score/inc - увеличивает балл текущего пользователя
//GET .../app/score/dec - уменьшает балл текущего пользователя
//GET .../app/score/get/current - показывает балл вошедшего пользователя
//GET .../app/score/get/{id} - показывает балл пользователя с указанным id
// (доступнотолько для залогиненных пользователей)

    @GetMapping("/score/get/current")
    public UserDto getScoreCurrent(Principal principal) {
        return userService.findDtoByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("unable to find user by username: " + principal.getName()));
    }

    // http://localhost:8189/security/unsecured/encode?password=100
    @GetMapping("/unsecured/encode")
    public String encodePassword(@RequestParam String password) {
        return securityService.encodePassword(password);
    }

    @GetMapping("/score/get/{id}")
    public UserDto getScoreById(@PathVariable String id) {
        return userService.findDtoById(Long.parseLong(id))
                .orElseThrow(() -> new RuntimeException("unable to find user by id: " + id));
    }

    @GetMapping("/score/inc")
    public UserDto incScoreCurrent(Principal principal) {
        return userService.addScoreByUsername(principal.getName(), 1);
    }

    @GetMapping("/score/dec")
    public UserDto decScoreCurrent(Principal principal) {
        return userService.addScoreByUsername(principal.getName(), -1);
    }
}
