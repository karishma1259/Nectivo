package com.nectivo.service;

import com.nectivo.dto.DonorDto;

import java.util.List;

public interface DonorService {
    DonorDto createDonor(DonorDto dto);
    DonorDto updateDonor(Long id, DonorDto dto);
    DonorDto getDonor(Long id);
    List<DonorDto> getAllDonors();
    void deleteDonor(Long id);
    DonorDto clearHealthScreening(Long id);
}
