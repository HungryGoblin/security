package ru.geekbrains.security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.security.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
