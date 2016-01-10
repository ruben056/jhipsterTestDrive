package be.rds.com.repository;

import be.rds.com.domain.Timesheet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Timesheet entity.
 */
public interface TimesheetRepository extends JpaRepository<Timesheet,Long> {

    @Query("select timesheet from Timesheet timesheet where timesheet.timesheet2user.login = ?#{principal.username}")
    List<Timesheet> findByTimesheet2userIsCurrentUser();

    @Query("select DISTINCT t from Timesheet t LEFT OUTER JOIN FETCH t.timeRegistrations where t.year = ?1 and t.month = ?2")
    List<Timesheet> findTimesheetInfoByYear(int year, int month);

    @Query("select DISTINCT t from Timesheet t LEFT OUTER JOIN FETCH t.timeRegistrations")
    java.util.List<Timesheet> findAll();

    @Query("select DISTINCT t from Timesheet t LEFT OUTER JOIN FETCH t.timeRegistrations where t.id = ?1")
    Timesheet findOne(Long id);
}
