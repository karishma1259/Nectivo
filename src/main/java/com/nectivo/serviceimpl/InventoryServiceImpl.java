package com.nectivo.serviceimpl;

import com.nectivo.dto.MilkBottleDto;
import com.nectivo.entity.Donation;
import com.nectivo.entity.MilkBottle;
import com.nectivo.enums.BottleStatus;
import com.nectivo.enums.DonationStatus;
import com.nectivo.exception.BadRequestException;
import com.nectivo.exception.ResourceNotFoundException;
import com.nectivo.repository.DonationRepository;
import com.nectivo.repository.MilkBottleRepository;
import com.nectivo.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final MilkBottleRepository bottleRepository;
    private final DonationRepository donationRepository;

    @Override
    public MilkBottleDto createBottleFromDonation(MilkBottleDto dto) {
        Donation donation = donationRepository.findById(dto.getDonationId())
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found with id: " + dto.getDonationId()));

        if (donation.getStatus() != DonationStatus.APPROVED && donation.getStatus() != DonationStatus.COLLECTED) {
            throw new BadRequestException("Donation must be approved/collected before creating a bottle");
        }

        MilkBottle bottle = MilkBottle.builder()
                .bottleCode("NM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .donation(donation)
                .quantityMl(dto.getQuantityMl())
                .storageLocation(dto.getStorageLocation())
                .expiryDate(dto.getExpiryDate() != null ? dto.getExpiryDate() : LocalDate.now().plusMonths(6))
                .status(BottleStatus.AVAILABLE)
                .build();

        return toDto(bottleRepository.save(bottle));
    }

    @Override
    public List<MilkBottleDto> getAllBottles() {
        refreshExpiredBottles();
        return bottleRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<MilkBottleDto> getAvailableBottles() {
        refreshExpiredBottles();
        return bottleRepository.findByStatus(BottleStatus.AVAILABLE).stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public MilkBottleDto markExpired(Long id) {
        MilkBottle bottle = bottleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bottle not found with id: " + id));
        bottle.setStatus(BottleStatus.EXPIRED);
        return toDto(bottleRepository.save(bottle));
    }

    @Override
    public void refreshExpiredBottles() {
        List<MilkBottle> expired = bottleRepository.findByExpiryDateBeforeAndStatus(LocalDate.now(), BottleStatus.AVAILABLE);
        expired.forEach(b -> b.setStatus(BottleStatus.EXPIRED));
        bottleRepository.saveAll(expired);
    }

    private MilkBottleDto toDto(MilkBottle bottle) {
        MilkBottleDto dto = new MilkBottleDto();
        dto.setId(bottle.getId());
        dto.setBottleCode(bottle.getBottleCode());
        dto.setDonationId(bottle.getDonation().getId());
        dto.setQuantityMl(bottle.getQuantityMl());
        dto.setStorageLocation(bottle.getStorageLocation());
        dto.setExpiryDate(bottle.getExpiryDate());
        dto.setStatus(bottle.getStatus());
        return dto;
    }
}
