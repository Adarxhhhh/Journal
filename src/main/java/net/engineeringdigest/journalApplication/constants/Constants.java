package net.engineeringdigest.journalApplication.constants;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Constants {
    private String weatherApi = "weather_api";
    private String weatherApiKey = "<apiKey>";
    private String city = "<city>";
}
