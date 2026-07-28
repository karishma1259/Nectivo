package com.nectivo.service;

import com.nectivo.dto.BabyDto;

import java.util.List;

public interface BabyService {
    BabyDto createBaby(BabyDto dto);
    BabyDto updateBaby(Long id, BabyDto dto);
    BabyDto getBaby(Long id);
    List<BabyDto> getAllBabies();
    void deleteBaby(Long id);
}
