package cz.moravec.model.projections;


import java.util.Date;

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

    public void setHumidityAverage(double humidityAverage) {
        this.humidityAverage = humidityAverage;
    }

    public double getPressureAverage() {
        return pressureAverage;
    }

    public void setPressureAverage(double pressureAverage) {
        this.pressureAverage = pressureAverage;
    }

    public double getTemperatureAverage() {
        return temperatureAverage;
    }

    public void setTemperatureAverage(double temperatureAverage) {
        this.temperatureAverage = temperatureAverage;
    }
}
