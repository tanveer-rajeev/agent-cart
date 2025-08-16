package com.tanveer.authservice.config;

import com.tanveer.authservice.domain.UserEntity;
import com.tanveer.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<UserEntity> credential = userRepository.findByEmail(email);
    if (credential.isEmpty()) {
      throw new UsernameNotFoundException("No organizer found by given email or phone number: " + email);
    }
    UserEntity userEntity = credential.get();
    return new org.springframework.security.core.userdetails
      .User(userEntity.getEmail(), userEntity.getPassword(), true,
      true, true, true,
      getGrantedAuthorities(userEntity.getRole()));
  }

  private Collection<? extends GrantedAuthority> getGrantedAuthorities(String role) {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role));
  }

}
