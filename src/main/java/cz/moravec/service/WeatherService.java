package cz.moravec.service;

import com.fasterxml.jackson.core.JsonParser;
import com.jayway.jsonpath.JsonPath;
import cz.moravec.config.WeatherProperties;
import cz.moravec.model.Measurement;
import net.minidev.json.JSONArray;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@Service
public class WeatherService {

    private static String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?id=${TOWN_ID}&APPID=${API_KEY}";
    private static String TOWN_ID_PLACEHOLDER = "${TOWN_ID}";
    private static String API_KEY_PLACEHOLDER = "${API_KEY}";
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;
    private final WeatherProperties properties;

    public WeatherService(WeatherProperties properties) {
        this.properties = properties;
    }


    public Measurement downloadActualDataForTown(int id) throws Exception {
        String result = downloadRawDataForTown(id);
        return parseMeasurement(result);
    }

    private Measurement parseMeasurement(String result) {
        double temperature = JsonPath.read(result, "$.main.temp");
        double humidity = JsonPath.read(result, "$.main.humidity");
        double pressure = JsonPath.read(result, "$.main.pressure");
        return new Measurement(temperature, pressure, humidity);
    }

    private String downloadRawDataForTown(int id) throws IOException {
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

    private String generateUrlForTownId(int id) {
        return WEATHER_URL.replace(TOWN_ID_PLACEHOLDER, String.valueOf(id)).replace(API_KEY_PLACEHOLDER, properties.getApiKey());
    }
}
