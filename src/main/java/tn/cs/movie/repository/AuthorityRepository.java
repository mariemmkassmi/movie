package tn.cs.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.cs.movie.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
