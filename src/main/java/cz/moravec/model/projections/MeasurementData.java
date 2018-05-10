package cz.moravec.model.projections;

import java.util.Date;

public class MeasurementData {
    private Date creationTime = new Date();
    private String townName;
    private double temperature;
    private double pressure;
    private double humidity;

    public Date getCreationTime() {
        return creationTime;
    }

    public String getTownName() {
        return townName;
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

    public void setTownName(String townName) {
        this.townName = townName;
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
