package cz.moravec.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * For getting and validating properties.
 */
@Component
@ConfigurationProperties("weather")
public class WeatherProperties {
    private String apiKey = "040abd4ec254d0fce692abfbf8824333";
    private int expireAfterSeconds = 1209600; //14 days
    private int updateAfterSeconds = 3600; // 1 hour
    private boolean readOnlyMode = false;

    public int getExpireAfterSeconds() {
        return expireAfterSeconds;
    }

    public void setExpireAfterSeconds(int expireAfterSeconds) {
        if (expireAfterSeconds < 0) {
            this.expireAfterSeconds = 0;
            return;
        }
        this.expireAfterSeconds = expireAfterSeconds;
    }

    public int getUpdateAfterSeconds() {
        return updateAfterSeconds;
    }

    public void setUpdateAfterSeconds(int updateAfterSeconds) {
        if (updateAfterSeconds < 61) { //API call limit
            this.updateAfterSeconds = 61;
            return;
        }
        this.updateAfterSeconds = updateAfterSeconds;
    }


    public boolean isReadOnlyMode() {
        return readOnlyMode;
    }

    public boolean getReadOnlyMode() {
        return readOnlyMode;
    }

    public void setReadOnlyMode(boolean readOnlyMode) {
        this.readOnlyMode = readOnlyMode;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
