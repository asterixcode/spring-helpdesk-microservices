package com.asterixcode.authserviceapi.service;

import com.asterixcode.authserviceapi.repository.UserRepository;
import com.asterixcode.authserviceapi.security.dto.UserDetailsDTO;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
    final var entity =
        repository
            .findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

    return UserDetailsDTO.builder()
        .id(entity.getId())
        .name(entity.getName())
        .username(entity.getEmail())
        .password(entity.getPassword())
        .authorities(
            entity.getProfiles().stream()
                .map(profile -> new SimpleGrantedAuthority(profile.getDescription()))
                .collect(Collectors.toSet()))
        .build();
  }
}
