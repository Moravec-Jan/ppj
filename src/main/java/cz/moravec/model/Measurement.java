package cz.moravec.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import java.util.Date;


@SuppressWarnings("unused") // used in thymeleaf template
@Document(collection = Measurement.COLLECTION_NAME)
public class Measurement {
    public static final String COLLECTION_NAME = "measurement";
    public static final String CREATION_TIME_NAME = "creationTime";
    public static final String TOWN_ID_NAME = "townId";
    public static final String ID_NAME = "_id";
    public static final String HUMIDITY_NAME = "humidity";
    public static final String TEMPERATURE_NAME = "temperature";
    public static final String PRESSURE_NAME = "pressure";

    //using String for easy parsing
    @Id
    @GeneratedValue
    private String id;

    // used for expiration index
    private Date creationTime = new Date();

    private long townId;

    private double temperature;
    private double pressure;
    private double humidity;

    public Measurement(long townId, double temperature, double pressure, double humidity) {
        this.townId = townId;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public Measurement() {
    }

    public String getId() {
        return id;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Measurement [" + "id=" + id + ", town_id=" + townId + " , temperature=" + temperature + ", humidity=" + humidity + ", pressure=" + pressure + "]";
    }


    public Date getCreationTime() {
        return creationTime;
    }

    public long getTownId() {
        return townId;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }
}
