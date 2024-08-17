package br.com.daniel.authserviceapi.services;

import br.com.daniel.authserviceapi.repositories.UserRepository;
import br.com.daniel.authserviceapi.security.dtos.UserDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var user = userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return UserDetailsDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getProfiles().stream()
                        .map(x -> new SimpleGrantedAuthority(x.getDescription())).collect(Collectors.toSet()))
                .password(user.getPassword())
                .build();
    }
}
