package testsystem.security;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import testsystem.repositories.UserRepository;


@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        testsystem.models.User user = userRepository.findByLogin(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found by name: " + username);
        }
        return User.withUsername(user.getLogin())
                .password(user.getPassword())
                .roles(user.getUserGroup().getName()).build();
    }

}