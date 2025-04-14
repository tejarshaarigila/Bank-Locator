package com.main.bank_locator.service;

import com.main.bank_locator.dto.BankDto;
import com.main.bank_locator.dto.LocationDto;

import java.util.List;

public interface BankService {
    List<BankDto> getNearbyBanks(LocationDto location);
}
