package dotseven.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dotseven.backend.entity.User;
import dotseven.backend.service.UserService;

import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
        return mapToUserDetails(user);
    }

    private UserDetailsImpl mapToUserDetails(User user) {
        UserDetailsImpl userDetailsImpl = new UserDetailsImpl();
        userDetailsImpl.setUsername(user.getUsername());
        userDetailsImpl.setPassword(user.getPasswordHash());
        userDetailsImpl.setAuthorities(List.of(new SimpleGrantedAuthority(WebSecurityConfig.USER)));
        return userDetailsImpl;
    }
}
