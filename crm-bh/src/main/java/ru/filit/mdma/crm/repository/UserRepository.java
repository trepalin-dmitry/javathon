package ru.filit.mdma.crm.repository;

import ru.filit.mdma.crm.entity.user.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> getByUsername(String username);
}
