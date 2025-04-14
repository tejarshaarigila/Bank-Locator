package com.main.bank_locator.controller;

import com.main.bank_locator.dto.BankDto;
import com.main.bank_locator.dto.LocationDto;
import com.main.bank_locator.service.BankService;
import com.main.bank_locator.service.GeoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banks")
@RequiredArgsConstructor
public class BankController {

    private final GeoService geoService;
    private final BankService bankService;

    @GetMapping
    public List<BankDto> getBanksByZip(@RequestParam String zip) {
        LocationDto location = geoService.getCoordinatesFromZip(zip);
        return bankService.getNearbyBanks(location);
    }
}
