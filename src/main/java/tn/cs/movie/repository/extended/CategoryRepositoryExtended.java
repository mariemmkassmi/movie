package tn.cs.movie.repository.extended;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.cs.movie.domain.Category;
import tn.cs.movie.repository.CategoryRepository;

/**
 * Spring Data JPA repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepositoryExtended extends CategoryRepository {}
