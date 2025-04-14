package com.main.bank_locator.service;

import com.main.bank_locator.dto.LocationDto;

public interface GeoService {
    LocationDto getCoordinatesFromZip(String zipCode);
}
