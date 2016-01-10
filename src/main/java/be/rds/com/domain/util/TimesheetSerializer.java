package be.rds.com.domain.util;

import be.rds.com.domain.Timesheet;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hibernate.Hibernate;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Because jackson has issues with bidirectional relations in jpa entity mappings
 * created this specific mapper
 * Issue was:
 * * Timesheet references List<TimeRegistrations>
 * * TimeRegistration references timesheet
 * --> json just keeps on serializing recursively...
 *
 * Created by ruben on 1/7/16.
 */
public class TimesheetSerializer extends JsonSerializer<Timesheet> {

    public static TimesheetSerializer INSTANCE = new TimesheetSerializer();

    private TimesheetSerializer(){};

    @Override
    public void serialize(Timesheet timesheet, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", timesheet.getId());
        jsonGenerator.writeStringField("description", timesheet.getDescription());
        jsonGenerator.writeNumberField("year", timesheet.getYear());
        jsonGenerator.writeNumberField("month", timesheet.getMonth());

        jsonGenerator.writeObjectField("timesheet2user", timesheet.getTimesheet2user());

        if(Hibernate.isInitialized(timesheet.getTimeRegistrations())){
            jsonGenerator.writeArrayFieldStart("timeRegistrations");
            timesheet.getTimeRegistrations().stream().forEach(timeRegistration -> {
                try{
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeNumberField("id", timeRegistration.getId());
                    jsonGenerator.writeStringField("rawUserInput", timeRegistration.getRawUserInput());
                    jsonGenerator.writeStringField("formattedAsTime", timeRegistration.getFormattedAsTime());
                    jsonGenerator.writeStringField("formattedAsDecimalHours", timeRegistration.getFormattedAsDecimalHours());
                    jsonGenerator.writeEndObject();

                }catch(IOException ioe){
                    throw new UncheckedIOException("IOException while serializing timesheet object", ioe);
                }
            });
            jsonGenerator.writeEndArray();
        }
        jsonGenerator.writeEndObject();
    }

    @Override
    public Class<Timesheet> handledType() {
        return Timesheet.class;
    }
}
