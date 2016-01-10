package be.rds.com.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "time_registration")
public class TimeRegistration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "raw_user_input")
    private String rawUserInput;

    @Column(name = "formatted_as_time")
    private String formattedAsTime;

    @Column(name = "formatted_as_decimal_hours")
    private String formattedAsDecimalHours;

    @ManyToOne
    @JoinColumn(name = "timesheet_id")
    private Timesheet timesheet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRawUserInput() {
        return rawUserInput;
    }

    public void setRawUserInput(String rawUserInput) {
        this.rawUserInput = rawUserInput;
    }

    public String getFormattedAsTime() {
        return formattedAsTime;
    }

    public void setFormattedAsTime(String formattedAsTime) {
        this.formattedAsTime = formattedAsTime;
    }

    public String getFormattedAsDecimalHours() {
        return formattedAsDecimalHours;
    }

    public void setFormattedAsDecimalHours(String formattedAsDecimalHours) {
        this.formattedAsDecimalHours = formattedAsDecimalHours;
    }

    public Timesheet getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeRegistration timeRegistration = (TimeRegistration) o;
        return Objects.equals(id, timeRegistration.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TimeRegistration{" +
            "id=" + id +
            ", rawUserInput='" + rawUserInput + "'" +
            ", formattedAsTime='" + formattedAsTime + "'" +
            ", formattedAsDecimalHours='" + formattedAsDecimalHours + "'" +
            '}';
    }
}
