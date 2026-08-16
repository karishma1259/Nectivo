package com.nectivo.service;

import com.nectivo.dto.MilkBottleDto;

import java.util.List;

public interface InventoryService {
    MilkBottleDto createBottleFromDonation(MilkBottleDto dto);
    List<MilkBottleDto> getAllBottles();
    List<MilkBottleDto> getAvailableBottles();
    MilkBottleDto markExpired(Long id);
    void refreshExpiredBottles();
}
