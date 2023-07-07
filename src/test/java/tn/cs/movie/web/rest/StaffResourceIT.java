package tn.cs.movie.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tn.cs.movie.IntegrationTest;
import tn.cs.movie.domain.Staff;
import tn.cs.movie.domain.enumeration.Role;
import tn.cs.movie.repository.StaffRepository;

/**
 * Integration tests for the {@link StaffResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StaffResourceIT {

    private static final Role DEFAULT_ROLE = Role.Director;
    private static final Role UPDATED_ROLE = Role.Author;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/staff";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStaffMockMvc;

    private Staff staff;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Staff createEntity(EntityManager em) {
        Staff staff = new Staff().role(DEFAULT_ROLE).firstName(DEFAULT_FIRST_NAME).lastName(DEFAULT_LAST_NAME);
        return staff;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Staff createUpdatedEntity(EntityManager em) {
        Staff staff = new Staff().role(UPDATED_ROLE).firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME);
        return staff;
    }

    @BeforeEach
    public void initTest() {
        staff = createEntity(em);
    }

    @Test
    @Transactional
    void createStaff() throws Exception {
        int databaseSizeBeforeCreate = staffRepository.findAll().size();
        // Create the Staff
        restStaffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isCreated());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeCreate + 1);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testStaff.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testStaff.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    @Transactional
    void createStaffWithExistingId() throws Exception {
        // Create the Staff with an existing ID
        staff.setId(1L);

        int databaseSizeBeforeCreate = staffRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        // Get all the staffList
        restStaffMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staff.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)));
    }

    @Test
    @Transactional
    void getStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        // Get the staff
        restStaffMockMvc
            .perform(get(ENTITY_API_URL_ID, staff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(staff.getId().intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME));
    }

    @Test
    @Transactional
    void getNonExistingStaff() throws Exception {
        // Get the staff
        restStaffMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        int databaseSizeBeforeUpdate = staffRepository.findAll().size();

        // Update the staff
        Staff updatedStaff = staffRepository.findById(staff.getId()).get();
        // Disconnect from session so that the updates on updatedStaff are not directly saved in db
        em.detach(updatedStaff);
        updatedStaff.role(UPDATED_ROLE).firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME);

        restStaffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStaff.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStaff))
            )
            .andExpect(status().isOk());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testStaff.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testStaff.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void putNonExistingStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, staff.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(staff))
            )
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(staff))
            )
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStaffWithPatch() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        int databaseSizeBeforeUpdate = staffRepository.findAll().size();

        // Update the staff using partial update
        Staff partialUpdatedStaff = new Staff();
        partialUpdatedStaff.setId(staff.getId());

        partialUpdatedStaff.role(UPDATED_ROLE);

        restStaffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStaff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStaff))
            )
            .andExpect(status().isOk());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testStaff.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testStaff.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    @Transactional
    void fullUpdateStaffWithPatch() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        int databaseSizeBeforeUpdate = staffRepository.findAll().size();

        // Update the staff using partial update
        Staff partialUpdatedStaff = new Staff();
        partialUpdatedStaff.setId(staff.getId());

        partialUpdatedStaff.role(UPDATED_ROLE).firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME);

        restStaffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStaff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStaff))
            )
            .andExpect(status().isOk());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testStaff.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testStaff.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, staff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(staff))
            )
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(staff))
            )
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        int databaseSizeBeforeDelete = staffRepository.findAll().size();

        // Delete the staff
        restStaffMockMvc
            .perform(delete(ENTITY_API_URL_ID, staff.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
