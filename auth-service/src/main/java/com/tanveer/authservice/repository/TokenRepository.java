package com.tanveer.authservice.repository;

import com.tanveer.authservice.domain.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {
  @Query(value = """
      select t from TokenEntity t inner join UserEntity u\s
      on t.userEntity.email = u.email\s
      where u.email = :email and (t.expired = false or t.revoked = false)\s
      """)
  List<TokenEntity> findAllValidTokenByUser(String email);

  Optional<TokenEntity> findByToken(String tokenEntity);
}
