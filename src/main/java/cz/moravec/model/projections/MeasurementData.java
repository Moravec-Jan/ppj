package cz.moravec.model.projections;

import java.util.Date;

/**
 * Projection for getting only data we need.
 */
@SuppressWarnings("unused") // used by jpa repository
public class MeasurementData {
    private Date creationTime = new Date();
    private String townName;
    private double temperature;
    private double pressure;
    private double humidity;

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

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }
}
