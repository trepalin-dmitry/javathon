package ru.filit.mdma.crm.web;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.filit.mdma.crm.entity.user.Role;
import ru.filit.mdma.crm.entity.user.User;

import java.util.Collections;
import java.util.Objects;

public class AuthUser extends org.springframework.security.core.userdetails.User {

    private final String username;
    private final String password;
    private final Role role;

    public AuthUser(User user) {
        super(user.getUsername(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getValue())));
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AuthUser authUser = (AuthUser) o;
        return Objects.equals(username, authUser.username) && Objects.equals(password, authUser.password) && role == authUser.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password, role);
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
