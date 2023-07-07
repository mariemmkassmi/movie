package tn.cs.movie.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.cs.movie.domain.Category;
import tn.cs.movie.repository.extended.CategoryRepositoryExtended;
import tn.cs.movie.service.CategoryService;

/**
 * Service Implementation for managing {@link Category}.
 */
@Service
@Transactional
public class CategoryServiceExtended extends CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryServiceExtended.class);

    private final CategoryRepositoryExtended categoryRepositoryExtended;


    public CategoryServiceExtended(CategoryRepositoryExtended categoryRepositoryExtended) {
        super(categoryRepositoryExtended);
        this.categoryRepositoryExtended = categoryRepositoryExtended;
    }
}
