package com.example.SpringBootDiaryApp.config.security.jwtToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
    @Query(value = """
      select t from JwtToken t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.isExpired = false or t.isRevoked = false)\s
      """)
    List<JwtToken> findAllValidTokenByUser(Long id);
    Optional<JwtToken> findByJwtToken(String token);
}
