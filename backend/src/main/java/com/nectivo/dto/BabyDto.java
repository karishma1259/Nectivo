package com.nectivo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BabyDto {
    private Long id;

    @NotBlank
    private String babyName;

    private LocalDate dateOfBirth;
    private Double birthWeightKg;

    @NotBlank
    private String parentName;

    @NotBlank
    private String parentContact;

    private boolean nicuAdmitted;
    private String diagnosisNotes;
}
