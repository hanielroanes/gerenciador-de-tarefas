package br.com.gerenciador.tarfas.userDomain.service;

import br.com.gerenciador.tarfas.userDomain.models.User;
import br.com.gerenciador.tarfas.userDomain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Transient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service("UserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(username);
        if (user == null){
            throw new UsernameNotFoundException("Email or password incorrect!");
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getAccessLevel().toString());
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(authority);
        return new org.springframework.security.core.userdetails.User
                (user.getEmail(),
                        user.getPassWord(),
                        authorities);
    }
}
