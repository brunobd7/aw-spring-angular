package com.algaworks.algamoney.api.security;

import com.algaworks.algamoney.api.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SystemUser extends User {

    private Users users;

    public SystemUser(Users users, Collection<? extends GrantedAuthority> authorities) {
        super(users.getEmail(), users.getPassword(), authorities);
        this.users = users;
    }

    public Users getUsers() {
        return users;
    }
}
