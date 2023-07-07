package tn.cs.movie.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.cs.movie.domain.Staff;
import tn.cs.movie.repository.StaffRepository;

/**
 * Service Implementation for managing {@link Staff}.
 */
@Service
@Transactional
public class StaffService {

    private final Logger log = LoggerFactory.getLogger(StaffService.class);

    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    /**
     * Save a staff.
     *
     * @param staff the entity to save.
     * @return the persisted entity.
     */
    public Staff save(Staff staff) {
        log.debug("Request to save Staff : {}", staff);
        return staffRepository.save(staff);
    }

    /**
     * Update a staff.
     *
     * @param staff the entity to save.
     * @return the persisted entity.
     */
    public Staff update(Staff staff) {
        log.debug("Request to update Staff : {}", staff);
        return staffRepository.save(staff);
    }

    /**
     * Partially update a staff.
     *
     * @param staff the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Staff> partialUpdate(Staff staff) {
        log.debug("Request to partially update Staff : {}", staff);

        return staffRepository
            .findById(staff.getId())
            .map(existingStaff -> {
                if (staff.getRole() != null) {
                    existingStaff.setRole(staff.getRole());
                }
                if (staff.getFirstName() != null) {
                    existingStaff.setFirstName(staff.getFirstName());
                }
                if (staff.getLastName() != null) {
                    existingStaff.setLastName(staff.getLastName());
                }

                return existingStaff;
            })
            .map(staffRepository::save);
    }

    /**
     * Get all the staff.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Staff> findAll() {
        log.debug("Request to get all Staff");
        return staffRepository.findAll();
    }

    /**
     * Get one staff by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Staff> findOne(Long id) {
        log.debug("Request to get Staff : {}", id);
        return staffRepository.findById(id);
    }

    /**
     * Delete the staff by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Staff : {}", id);
        staffRepository.deleteById(id);
    }
}
