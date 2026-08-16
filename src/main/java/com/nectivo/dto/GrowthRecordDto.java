package com.nectivo.dto;
import lombok.Data;
import java.time.LocalDate;
@Data
public class GrowthRecordDto {
    private Long id;
    private Long babyId;
    private LocalDate recordDate;
    private Double weightKg;
    private Double heightCm;
    private String notes;
}