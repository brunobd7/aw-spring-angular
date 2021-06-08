package com.algaworks.algamoney.api.security;

import com.algaworks.algamoney.api.model.Users;
import com.algaworks.algamoney.api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

//SERVICE BUSCA USUARIO NO BANCO E VALIDA COM SPRING
@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //CONSULTA O USUARIO E SUAS PERMISSOES E VALIDA COM AJUDA DO SPRING
        Optional<Users> usersOptional = usersRepository.findByEmail(email);
        Users users = usersOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
//        return new User(email, users.getPassword(), getPermissions(users));
        return new SystemUser(users,getPermissions(users)); /**AJUSTANDO CUSTOM ENHANCER TOKEN PARA INFO ADICIONAIS NO TOKEN*/
    }

    private Collection<SimpleGrantedAuthority> getPermissions(Users users) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        users.getPermissions().forEach( permission
                -> authorities.add(new SimpleGrantedAuthority(permission.getDescription())));

        return authorities;
    }
}
