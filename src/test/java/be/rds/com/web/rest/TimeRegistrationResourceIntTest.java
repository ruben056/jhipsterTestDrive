package be.rds.com.web.rest;

import be.rds.com.Application;
import be.rds.com.domain.TimeRegistration;
import be.rds.com.repository.TimeRegistrationRepository;

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
 * Test class for the TimeRegistrationResource REST controller.
 *
 * @see TimeRegistrationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TimeRegistrationResourceIntTest {

    private static final String DEFAULT_RAW_USER_INPUT = "AAAAA";
    private static final String UPDATED_RAW_USER_INPUT = "BBBBB";
    private static final String DEFAULT_FORMATTED_AS_TIME = "AAAAA";
    private static final String UPDATED_FORMATTED_AS_TIME = "BBBBB";
    private static final String DEFAULT_FORMATTED_AS_DECIMAL_HOURS = "AAAAA";
    private static final String UPDATED_FORMATTED_AS_DECIMAL_HOURS = "BBBBB";

    @Inject
    private TimeRegistrationRepository timeRegistrationRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTimeRegistrationMockMvc;

    private TimeRegistration timeRegistration;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TimeRegistrationResource timeRegistrationResource = new TimeRegistrationResource();
        ReflectionTestUtils.setField(timeRegistrationResource, "timeRegistrationRepository", timeRegistrationRepository);
        this.restTimeRegistrationMockMvc = MockMvcBuilders.standaloneSetup(timeRegistrationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        timeRegistration = new TimeRegistration();
        timeRegistration.setRawUserInput(DEFAULT_RAW_USER_INPUT);
        timeRegistration.setFormattedAsTime(DEFAULT_FORMATTED_AS_TIME);
        timeRegistration.setFormattedAsDecimalHours(DEFAULT_FORMATTED_AS_DECIMAL_HOURS);
    }

    @Test
    @Transactional
    public void createTimeRegistration() throws Exception {
        int databaseSizeBeforeCreate = timeRegistrationRepository.findAll().size();

        // Create the TimeRegistration

        restTimeRegistrationMockMvc.perform(post("/api/timeRegistrations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeRegistration)))
                .andExpect(status().isCreated());

        // Validate the TimeRegistration in the database
        List<TimeRegistration> timeRegistrations = timeRegistrationRepository.findAll();
        assertThat(timeRegistrations).hasSize(databaseSizeBeforeCreate + 1);
        TimeRegistration testTimeRegistration = timeRegistrations.get(timeRegistrations.size() - 1);
        assertThat(testTimeRegistration.getRawUserInput()).isEqualTo(DEFAULT_RAW_USER_INPUT);
        assertThat(testTimeRegistration.getFormattedAsTime()).isEqualTo(DEFAULT_FORMATTED_AS_TIME);
        assertThat(testTimeRegistration.getFormattedAsDecimalHours()).isEqualTo(DEFAULT_FORMATTED_AS_DECIMAL_HOURS);
    }

    @Test
    @Transactional
    public void getAllTimeRegistrations() throws Exception {
        // Initialize the database
        timeRegistrationRepository.saveAndFlush(timeRegistration);

        // Get all the timeRegistrations
        restTimeRegistrationMockMvc.perform(get("/api/timeRegistrations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(timeRegistration.getId().intValue())))
                .andExpect(jsonPath("$.[*].rawUserInput").value(hasItem(DEFAULT_RAW_USER_INPUT.toString())))
                .andExpect(jsonPath("$.[*].formattedAsTime").value(hasItem(DEFAULT_FORMATTED_AS_TIME.toString())))
                .andExpect(jsonPath("$.[*].formattedAsDecimalHours").value(hasItem(DEFAULT_FORMATTED_AS_DECIMAL_HOURS.toString())));
    }

    @Test
    @Transactional
    public void getTimeRegistration() throws Exception {
        // Initialize the database
        timeRegistrationRepository.saveAndFlush(timeRegistration);

        // Get the timeRegistration
        restTimeRegistrationMockMvc.perform(get("/api/timeRegistrations/{id}", timeRegistration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(timeRegistration.getId().intValue()))
            .andExpect(jsonPath("$.rawUserInput").value(DEFAULT_RAW_USER_INPUT.toString()))
            .andExpect(jsonPath("$.formattedAsTime").value(DEFAULT_FORMATTED_AS_TIME.toString()))
            .andExpect(jsonPath("$.formattedAsDecimalHours").value(DEFAULT_FORMATTED_AS_DECIMAL_HOURS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeRegistration() throws Exception {
        // Get the timeRegistration
        restTimeRegistrationMockMvc.perform(get("/api/timeRegistrations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeRegistration() throws Exception {
        // Initialize the database
        timeRegistrationRepository.saveAndFlush(timeRegistration);

		int databaseSizeBeforeUpdate = timeRegistrationRepository.findAll().size();

        // Update the timeRegistration
        timeRegistration.setRawUserInput(UPDATED_RAW_USER_INPUT);
        timeRegistration.setFormattedAsTime(UPDATED_FORMATTED_AS_TIME);
        timeRegistration.setFormattedAsDecimalHours(UPDATED_FORMATTED_AS_DECIMAL_HOURS);

        restTimeRegistrationMockMvc.perform(put("/api/timeRegistrations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeRegistration)))
                .andExpect(status().isOk());

        // Validate the TimeRegistration in the database
        List<TimeRegistration> timeRegistrations = timeRegistrationRepository.findAll();
        assertThat(timeRegistrations).hasSize(databaseSizeBeforeUpdate);
        TimeRegistration testTimeRegistration = timeRegistrations.get(timeRegistrations.size() - 1);
        assertThat(testTimeRegistration.getRawUserInput()).isEqualTo(UPDATED_RAW_USER_INPUT);
        assertThat(testTimeRegistration.getFormattedAsTime()).isEqualTo(UPDATED_FORMATTED_AS_TIME);
        assertThat(testTimeRegistration.getFormattedAsDecimalHours()).isEqualTo(UPDATED_FORMATTED_AS_DECIMAL_HOURS);
    }

    @Test
    @Transactional
    public void deleteTimeRegistration() throws Exception {
        // Initialize the database
        timeRegistrationRepository.saveAndFlush(timeRegistration);

		int databaseSizeBeforeDelete = timeRegistrationRepository.findAll().size();

        // Get the timeRegistration
        restTimeRegistrationMockMvc.perform(delete("/api/timeRegistrations/{id}", timeRegistration.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeRegistration> timeRegistrations = timeRegistrationRepository.findAll();
        assertThat(timeRegistrations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
