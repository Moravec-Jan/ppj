package cz.moravec.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.moravec.config.WeatherProperties;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import java.util.Date;


@Document(collection = Measurement.COLLECTION_NAME)
public class Measurement {
    public static final String COLLECTION_NAME = "measurement";
    public static final String CREATION_TIME_NAME = "creationTime";

    @Id
    @GeneratedValue
    private String id;

    // for expiration
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

    public Date getCreationTime() {
        return creationTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

//    public void setId(ObjectId id) {
//        this.id = id;
//    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public long getTownId() {
        return townId;
    }

    public void setTownId(long townId) {
        this.townId = townId;
    }

    @Override
    public String toString() {
        return "Measurement [" + "id=" + id + ", town_id=" + townId + " , temperature=" + temperature + ", humidity=" + humidity + ", pressure=" + pressure + "]";
    }
}
