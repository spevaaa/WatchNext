package hr.tvz.watchnext.watchnextapp.service;

import hr.tvz.watchnext.watchnextapp.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getAuthorities().stream()
                                .map(auth -> new SimpleGrantedAuthority(auth.getName()))
                                .collect(Collectors.toSet())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik nije pronađen: " + username));
    }
}