package com.nectivo.dto;

import com.nectivo.enums.DonationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DonationDto {
    private Long id;
    private Long donorId;
    private String donorName;
    private Double quantityMl;
    private LocalDateTime donationDate;
    private DonationStatus status;
    private String notes;
}