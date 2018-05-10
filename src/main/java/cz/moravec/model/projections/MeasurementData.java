package cz.moravec.model.projections;

import java.util.Date;

public class MeasurementData {
    private Date creationTime = new Date();

    private long townId;

    private double temperature;
    private double pressure;
    private double humidity;

    public Date getCreationTime() {
        return creationTime;
    }

    public long getTownId() {
        return townId;
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

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setTownId(long townId) {
        this.townId = townId;
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
