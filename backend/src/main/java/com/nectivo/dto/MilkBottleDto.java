package com.nectivo.dto;

import com.nectivo.enums.BottleStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MilkBottleDto {
    private Long id;
    private String bottleCode;
    private Long donationId;
    private Double quantityMl;
    private String storageLocation;
    private LocalDate expiryDate;
    private BottleStatus status;
}
