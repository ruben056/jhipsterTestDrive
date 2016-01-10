package be.rds.com.repository;

import be.rds.com.domain.TimeRegistration;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TimeRegistration entity.
 */
public interface TimeRegistrationRepository extends JpaRepository<TimeRegistration,Long> {

}
