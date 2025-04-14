package com.main.bank_locator.service.impl;

import com.main.bank_locator.dto.BankDto;
import com.main.bank_locator.dto.LocationDto;
import com.main.bank_locator.service.BankService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.*;
import java.util.*;

@Service
public class BankServiceImpl implements BankService {

    @Value("${google.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<BankDto> getNearbyBanks(LocationDto location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        int radiusInMeters = 16093; // ~10 miles

        String url = String.format(
            "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%f,%f&radius=%d&type=bank&key=%s",
            lat, lng, radiusInMeters, apiKey
        );

        String response = restTemplate.getForObject(url, String.class);
        JSONObject json = new JSONObject(response);
        JSONArray results = json.getJSONArray("results");

        List<BankDto> banks = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            JSONObject obj = results.getJSONObject(i);
            String name = obj.optString("name");
            String address = obj.optString("vicinity");

            JSONObject bankLocation = obj.getJSONObject("geometry").getJSONObject("location");
            double bankLat = bankLocation.getDouble("lat");
            double bankLng = bankLocation.getDouble("lng");

            double distance = calculateDistance(lat, lng, bankLat, bankLng);

            banks.add(new BankDto(name, address, distance));
        }

        return banks;
    }

    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double R = 3958.8;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = R * c;
        
        return Math.round(distance * 100.0) / 100.0;
    }
}
