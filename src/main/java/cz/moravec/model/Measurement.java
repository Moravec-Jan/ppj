package cz.moravec.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;

@Document(collection = Measurement.COLLECTION_NAME)
public class Measurement {
    public static final String COLLECTION_NAME = "measurements";

    @Id
    @Indexed(name = "expire_after_15_days", expireAfterSeconds = 60*60*24*14)
    @GeneratedValue
    private ObjectId id;

    private double temperature;
    private double pressure;
    private double humidity;

    public Measurement() {
    }

    public Measurement(double temperature, double pressure, double humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
    }

//    public Measurement(ObjectId id, double temperature, double pressure, double humidity) {
//        this.id = id;
//        this.temperature = temperature;
//        this.pressure = pressure;
//        this.humidity = humidity;
//    }


    public ObjectId getId() {
        return id;
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

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
