package com.main.bank_locator.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDto {
    private String bankName;
    private String bankAddress;
    private double distanceMiles;
}
