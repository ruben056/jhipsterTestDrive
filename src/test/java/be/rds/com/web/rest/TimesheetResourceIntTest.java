package be.rds.com.web.rest;

import be.rds.com.Application;
import be.rds.com.domain.Timesheet;
import be.rds.com.repository.TimesheetRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TimesheetResource REST controller.
 *
 * @see TimesheetResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TimesheetResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    @Inject
    private TimesheetRepository timesheetRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTimesheetMockMvc;

    private Timesheet timesheet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TimesheetResource timesheetResource = new TimesheetResource();
        ReflectionTestUtils.setField(timesheetResource, "timesheetRepository", timesheetRepository);
        this.restTimesheetMockMvc = MockMvcBuilders.standaloneSetup(timesheetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        timesheet = new Timesheet();
        timesheet.setDescription(DEFAULT_DESCRIPTION);
        timesheet.setMonth(DEFAULT_MONTH);
        timesheet.setYear(DEFAULT_YEAR);
    }

    @Test
    @Transactional
    public void createTimesheet() throws Exception {
        int databaseSizeBeforeCreate = timesheetRepository.findAll().size();

        // Create the Timesheet

        restTimesheetMockMvc.perform(post("/api/timesheets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timesheet)))
                .andExpect(status().isCreated());

        // Validate the Timesheet in the database
        List<Timesheet> timesheets = timesheetRepository.findAll();
        assertThat(timesheets).hasSize(databaseSizeBeforeCreate + 1);
        Timesheet testTimesheet = timesheets.get(timesheets.size() - 1);
        assertThat(testTimesheet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTimesheet.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testTimesheet.getYear()).isEqualTo(DEFAULT_YEAR);
    }

    @Test
    @Transactional
    public void getAllTimesheets() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheets
        restTimesheetMockMvc.perform(get("/api/timesheets?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(timesheet.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
                .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)));
    }

    @Test
    @Transactional
    public void getTimesheet() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get the timesheet
        restTimesheetMockMvc.perform(get("/api/timesheets/{id}", timesheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(timesheet.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR));
    }

    @Test
    @Transactional
    public void getNonExistingTimesheet() throws Exception {
        // Get the timesheet
        restTimesheetMockMvc.perform(get("/api/timesheets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimesheet() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

		int databaseSizeBeforeUpdate = timesheetRepository.findAll().size();

        // Update the timesheet
        timesheet.setDescription(UPDATED_DESCRIPTION);
        timesheet.setMonth(UPDATED_MONTH);
        timesheet.setYear(UPDATED_YEAR);

        restTimesheetMockMvc.perform(put("/api/timesheets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timesheet)))
                .andExpect(status().isOk());

        // Validate the Timesheet in the database
        List<Timesheet> timesheets = timesheetRepository.findAll();
        assertThat(timesheets).hasSize(databaseSizeBeforeUpdate);
        Timesheet testTimesheet = timesheets.get(timesheets.size() - 1);
        assertThat(testTimesheet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTimesheet.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testTimesheet.getYear()).isEqualTo(UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void deleteTimesheet() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

		int databaseSizeBeforeDelete = timesheetRepository.findAll().size();

        // Get the timesheet
        restTimesheetMockMvc.perform(delete("/api/timesheets/{id}", timesheet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Timesheet> timesheets = timesheetRepository.findAll();
        assertThat(timesheets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
