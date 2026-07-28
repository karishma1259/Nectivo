package com.nectivo.serviceimpl;

import com.nectivo.dto.DistributionDto;
import com.nectivo.entity.Baby;
import com.nectivo.entity.Distribution;
import com.nectivo.entity.MilkBottle;
import com.nectivo.enums.BottleStatus;
import com.nectivo.exception.BadRequestException;
import com.nectivo.exception.ResourceNotFoundException;
import com.nectivo.repository.BabyRepository;
import com.nectivo.repository.DistributionRepository;
import com.nectivo.repository.MilkBottleRepository;
import com.nectivo.service.DistributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DistributionServiceImpl implements DistributionService {

    private final DistributionRepository distributionRepository;
    private final MilkBottleRepository bottleRepository;
    private final BabyRepository babyRepository;

    @Override
    public DistributionDto distributeBottle(DistributionDto dto) {
        MilkBottle bottle = bottleRepository.findById(dto.getBottleId())
                .orElseThrow(() -> new ResourceNotFoundException("Bottle not found with id: " + dto.getBottleId()));

        if (bottle.getStatus() != BottleStatus.AVAILABLE) {
            throw new BadRequestException("Bottle is not available for distribution");
        }

        Baby baby = babyRepository.findById(dto.getBabyId())
                .orElseThrow(() -> new ResourceNotFoundException("Baby not found with id: " + dto.getBabyId()));

        bottle.setStatus(BottleStatus.DISTRIBUTED);
        bottleRepository.save(bottle);

        Distribution distribution = Distribution.builder()
                .bottle(bottle)
                .baby(baby)
                .approvedByDoctor(dto.getApprovedByDoctor())
                .remarks(dto.getRemarks())
                .build();

        return toDto(distributionRepository.save(distribution));
    }

    @Override
    public List<DistributionDto> getAllDistributions() {
        return distributionRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    private DistributionDto toDto(Distribution distribution) {
        DistributionDto dto = new DistributionDto();
        dto.setId(distribution.getId());
        dto.setBottleId(distribution.getBottle().getId());
        dto.setBottleCode(distribution.getBottle().getBottleCode());
        dto.setBabyId(distribution.getBaby().getId());
        dto.setBabyName(distribution.getBaby().getBabyName());
        dto.setApprovedByDoctor(distribution.getApprovedByDoctor());
        dto.setDistributedAt(distribution.getDistributedAt());
        dto.setRemarks(distribution.getRemarks());
        return dto;
    }
}
