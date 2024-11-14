package com.pollub.MovieApplication.service;

import com.pollub.MovieApplication.entity.User;
import com.pollub.MovieApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = repository.findByEmail(username); // Assuming 'email' is used as username

        return userDetail.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public String addUser(User userInfo) {
        // Encode password before saving the user
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }

    // Inner class implementing UserDetails
    private static class UserDetailsImpl implements UserDetails {
        private String username;
        private String password;
        private List<SimpleGrantedAuthority> authorities;

        public UserDetailsImpl(User userInfo) {
            this.username = userInfo.getEmail(); // Assuming 'email' is used as 'username'
            this.password = userInfo.getPassword();
            this.authorities = List.of(userInfo.getRoles().split(","))
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        @Override
        public Collection<? extends SimpleGrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true; // Implement your logic if needed
        }

        @Override
        public boolean isAccountNonLocked() {
            return true; // Implement your logic if needed
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true; // Implement your logic if needed
        }

        @Override
        public boolean isEnabled() {
            return true; // Implement your logic if needed
        }
    }
}
