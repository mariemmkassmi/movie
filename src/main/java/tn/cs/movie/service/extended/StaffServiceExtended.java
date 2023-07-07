package tn.cs.movie.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.cs.movie.repository.StaffRepository;
import tn.cs.movie.repository.extended.CategoryRepositoryExtended;
import tn.cs.movie.repository.extended.StaffRepositoryExtended;
import tn.cs.movie.service.StaffService;
@Service
@Transactional
public class StaffServiceExtended extends StaffService {

    private final Logger log = LoggerFactory.getLogger(StaffServiceExtended.class);

    private final StaffRepositoryExtended staffRepositoryExtended;



    public StaffServiceExtended(StaffRepositoryExtended staffRepositoryExtended) {
        super(staffRepositoryExtended);
        this.staffRepositoryExtended = staffRepositoryExtended;
    }
}
