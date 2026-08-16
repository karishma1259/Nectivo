package com.nectivo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DistributionDto {
    private Long id;
    private Long bottleId;
    private String bottleCode;
    private Long babyId;
    private String babyName;
    private String approvedByDoctor;
    private LocalDateTime distributedAt;
    private String remarks;
}
