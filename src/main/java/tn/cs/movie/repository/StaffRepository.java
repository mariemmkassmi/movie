package tn.cs.movie.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tn.cs.movie.domain.Staff;

/**
 * Spring Data JPA repository for the Staff entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {}
