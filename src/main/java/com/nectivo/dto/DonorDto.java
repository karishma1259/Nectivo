package com.nectivo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DonorDto {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String bloodGroup;
    private LocalDate dateOfBirth;
    private String address;
    private boolean healthScreeningCleared;
    private boolean active;
}
