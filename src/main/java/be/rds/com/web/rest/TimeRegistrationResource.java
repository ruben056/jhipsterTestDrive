package be.rds.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.rds.com.domain.TimeRegistration;
import be.rds.com.repository.TimeRegistrationRepository;
import be.rds.com.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TimeRegistration.
 */
@RestController
@RequestMapping("/api")
public class TimeRegistrationResource {

    private final Logger log = LoggerFactory.getLogger(TimeRegistrationResource.class);
        
    @Inject
    private TimeRegistrationRepository timeRegistrationRepository;
    
    /**
     * POST  /timeRegistrations -> Create a new timeRegistration.
     */
    @RequestMapping(value = "/timeRegistrations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeRegistration> createTimeRegistration(@RequestBody TimeRegistration timeRegistration) throws URISyntaxException {
        log.debug("REST request to save TimeRegistration : {}", timeRegistration);
        if (timeRegistration.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("timeRegistration", "idexists", "A new timeRegistration cannot already have an ID")).body(null);
        }
        TimeRegistration result = timeRegistrationRepository.save(timeRegistration);
        return ResponseEntity.created(new URI("/api/timeRegistrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("timeRegistration", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /timeRegistrations -> Updates an existing timeRegistration.
     */
    @RequestMapping(value = "/timeRegistrations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeRegistration> updateTimeRegistration(@RequestBody TimeRegistration timeRegistration) throws URISyntaxException {
        log.debug("REST request to update TimeRegistration : {}", timeRegistration);
        if (timeRegistration.getId() == null) {
            return createTimeRegistration(timeRegistration);
        }
        TimeRegistration result = timeRegistrationRepository.save(timeRegistration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("timeRegistration", timeRegistration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /timeRegistrations -> get all the timeRegistrations.
     */
    @RequestMapping(value = "/timeRegistrations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TimeRegistration> getAllTimeRegistrations() {
        log.debug("REST request to get all TimeRegistrations");
        return timeRegistrationRepository.findAll();
            }

    /**
     * GET  /timeRegistrations/:id -> get the "id" timeRegistration.
     */
    @RequestMapping(value = "/timeRegistrations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeRegistration> getTimeRegistration(@PathVariable Long id) {
        log.debug("REST request to get TimeRegistration : {}", id);
        TimeRegistration timeRegistration = timeRegistrationRepository.findOne(id);
        return Optional.ofNullable(timeRegistration)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /timeRegistrations/:id -> delete the "id" timeRegistration.
     */
    @RequestMapping(value = "/timeRegistrations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTimeRegistration(@PathVariable Long id) {
        log.debug("REST request to delete TimeRegistration : {}", id);
        timeRegistrationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("timeRegistration", id.toString())).build();
    }
}
