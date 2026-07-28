package com.nectivo.service;

import com.nectivo.dto.DistributionDto;

import java.util.List;

public interface DistributionService {
    DistributionDto distributeBottle(DistributionDto dto);
    List<DistributionDto> getAllDistributions();
}
