package com.nectivo.serviceimpl;

import com.nectivo.dto.DonationDto;
import com.nectivo.entity.Donation;
import com.nectivo.entity.Donor;
import com.nectivo.enums.DonationStatus;
import com.nectivo.exception.BadRequestException;
import com.nectivo.exception.ResourceNotFoundException;
import com.nectivo.repository.DonationRepository;
import com.nectivo.repository.DonorRepository;
import com.nectivo.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final DonorRepository donorRepository;

    @Override
    public DonationDto createDonation(DonationDto dto) {
        Donor donor = donorRepository.findById(dto.getDonorId())
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found with id: " + dto.getDonorId()));

        if (!donor.isHealthScreeningCleared()) {
            throw new BadRequestException("Donor has not cleared health screening yet");
        }

        Donation donation = Donation.builder()
                .donor(donor)
                .quantityMl(dto.getQuantityMl())
                .status(DonationStatus.PENDING)
                .notes(dto.getNotes())
                .build();

        return toDto(donationRepository.save(donation));
    }

    @Override
    public DonationDto updateStatus(Long id, String status) {
        Donation donation = findEntity(id);
        donation.setStatus(DonationStatus.valueOf(status.toUpperCase()));
        return toDto(donationRepository.save(donation));
    }

    @Override
    public DonationDto getDonation(Long id) {
        return toDto(findEntity(id));
    }

    @Override
    public List<DonationDto> getAllDonations() {
        return donationRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<DonationDto> getMyDonations(String email) {
        Donor donor = donorRepository.findByUser_Email(email)
                .orElseThrow(() -> new ResourceNotFoundException("No donor profile linked to this account yet."));
        return donationRepository.findByDonorId(donor.getId()).stream().map(this::toDto).collect(Collectors.toList());
    }

    private Donation findEntity(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found with id: " + id));
    }

    private DonationDto toDto(Donation donation) {
        DonationDto dto = new DonationDto();
        dto.setId(donation.getId());
        dto.setDonorId(donation.getDonor().getId());
        dto.setDonorName(donation.getDonor().getFullName());
        dto.setQuantityMl(donation.getQuantityMl());
        dto.setDonationDate(donation.getDonationDate());
        dto.setStatus(donation.getStatus());
        dto.setNotes(donation.getNotes());
        return dto;
    }
}