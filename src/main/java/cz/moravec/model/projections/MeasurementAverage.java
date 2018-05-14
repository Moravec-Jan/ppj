package cz.moravec.model.projections;


import java.util.Date;

/**
 * Projection for getting average data
 */
public class MeasurementAverage {
    public static final String HUMIDITY_AVERAGE = "humidityAverage";
    public static final String PRESSURE_AVERAGE = "pressureAverage";
    public static final String TEMPERATURE_AVERAGE = "temperatureAverage";


    private double humidityAverage;
    private double pressureAverage;
    private double temperatureAverage;
    private Date creationTime;

    public MeasurementAverage() {
    }

    public double getHumidityAverage() {
        return humidityAverage;
    }

    public double getPressureAverage() {
        return pressureAverage;
    }

    public double getTemperatureAverage() {
        return temperatureAverage;
    }

}
