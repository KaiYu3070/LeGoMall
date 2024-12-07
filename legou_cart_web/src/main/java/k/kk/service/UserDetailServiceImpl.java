package k.kk.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Store the list of roles
        List<GrantedAuthority> authorities = new ArrayList();
        // Add role
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        // Return user details
        return new User(username, "", authorities);
    }
}
