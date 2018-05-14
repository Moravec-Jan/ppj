package cz.moravec.service;

import com.jayway.jsonpath.JsonPath;
import cz.moravec.config.WeatherProperties;
import cz.moravec.model.Measurement;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Service for downloading weather data from Openweather server
 */
@Service
public class WeatherService {

    private static String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?id=${TOWN_ID}&APPID=${API_KEY}&units=metric";
    private static String TOWN_ID_PLACEHOLDER = "${TOWN_ID}";
    private static String API_KEY_PLACEHOLDER = "${API_KEY}";
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;
    private final WeatherProperties properties;

    public WeatherService(WeatherProperties properties) {
        this.properties = properties;
    }


    Measurement downloadActualDataForTown(long id) throws Exception {
        String result = downloadRawDataForTown(id);
        return parseMeasurement(id, result);
    }

    private Measurement parseMeasurement(long townId, String result) throws Exception {
        Object temperatureObject = JsonPath.read(result, "$.main.temp");
        Object humidityObject = JsonPath.read(result, "$.main.humidity");
        Object pressureObject = JsonPath.read(result, "$.main.pressure");
        double temperature = parseNumber(temperatureObject);
        double humidity = parseNumber(humidityObject);
        double pressure = parseNumber(pressureObject);

        return new Measurement(townId, temperature, pressure, humidity);
    }

    // we do this ugly thing, because number is sometimes parsed like Double sometimes like Integer
    private double parseNumber(Object number) throws Exception {
        double temperature;
        if (number instanceof Double) {
            temperature = (Double) number;
        } else if (number instanceof Integer) {
            temperature = ((Integer) number).doubleValue();
        } else {
            throw new Exception("Parse exception expected Double or Integer, but got " + number.getClass());
        }
        return temperature;
    }

    private String downloadRawDataForTown(long id) throws IOException {
        URL url = new URL(generateUrlForTownId(id));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }

    private String generateUrlForTownId(long id) {
        return WEATHER_URL.replace(TOWN_ID_PLACEHOLDER, String.valueOf(id)).replace(API_KEY_PLACEHOLDER, properties.getApiKey());
    }
}
