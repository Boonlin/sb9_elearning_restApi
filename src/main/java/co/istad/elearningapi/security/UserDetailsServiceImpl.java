package co.istad.elearningapi.security;

import co.istad.elearningapi.model.User;
import co.istad.elearningapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmailAndIsDeletedAndIsVerified(username,
                        false,
                        true)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid user!"));

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(user);

        System.out.println("Username: " + customUserDetails.getUsername());

        return customUserDetails;
    }
}
