package com.main.bank_locator.service.impl;

import com.main.bank_locator.dto.LocationDto;
import com.main.bank_locator.service.GeoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.*;

@Service
public class GeoServiceImpl implements GeoService {

    @Value("${google.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public LocationDto getCoordinatesFromZip(String zipCode) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + zipCode + "&key=" + apiKey;
        String response = restTemplate.getForObject(url, String.class);

        JSONObject json = new JSONObject(response);
        JSONArray results = json.getJSONArray("results");
        JSONObject location = results.getJSONObject(0)
                                     .getJSONObject("geometry")
                                     .getJSONObject("location");

        return new LocationDto(
            location.getDouble("lat"),
            location.getDouble("lng")
        );
    }
}
