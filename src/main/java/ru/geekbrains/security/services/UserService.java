package ru.geekbrains.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.security.dtos.UserDto;
import ru.geekbrains.security.entities.Role;
import ru.geekbrains.security.entities.User;
import ru.geekbrains.security.repositories.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


    @Transactional
    public UserDto addScoreByUsername(String userName, long score) {
        User user = findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", userName)));
        user.setScore(user.getScore() + score);
        return new UserDto(user);
    }

    public Optional<UserDto> findDtoByUsername(String username) {
        return findByUsername(username).map(UserDto::new);
    }

    public Optional<UserDto> findDtoById(long id) {
        return userRepository.findById(id).map(UserDto::new);
    }

}