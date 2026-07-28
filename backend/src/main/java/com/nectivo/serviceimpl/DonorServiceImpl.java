package com.nectivo.serviceimpl;

import com.nectivo.dto.DonorDto;
import com.nectivo.entity.Donor;
import com.nectivo.exception.ResourceNotFoundException;
import com.nectivo.repository.DonorRepository;
import com.nectivo.service.DonorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonorServiceImpl implements DonorService {

    private final DonorRepository donorRepository;

    @Override
    public DonorDto createDonor(DonorDto dto) {
        Donor donor = Donor.builder()
                .fullName(dto.getFullName())
                .bloodGroup(dto.getBloodGroup())
                .dateOfBirth(dto.getDateOfBirth())
                .address(dto.getAddress())
                .healthScreeningCleared(false)
                .active(true)
                .build();

        return toDto(donorRepository.save(donor));
    }

    @Override
    public DonorDto updateDonor(Long id, DonorDto dto) {
        Donor donor = findEntity(id);
        donor.setFullName(dto.getFullName());
        donor.setBloodGroup(dto.getBloodGroup());
        donor.setDateOfBirth(dto.getDateOfBirth());
        donor.setAddress(dto.getAddress());
        return toDto(donorRepository.save(donor));
    }

    @Override
    public DonorDto getDonor(Long id) {
        return toDto(findEntity(id));
    }

    @Override
    public List<DonorDto> getAllDonors() {
        return donorRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteDonor(Long id) {
        Donor donor = findEntity(id);
        donor.setActive(false);
        donorRepository.save(donor);
    }

    @Override
    public DonorDto clearHealthScreening(Long id) {
        Donor donor = findEntity(id);
        donor.setHealthScreeningCleared(true);
        return toDto(donorRepository.save(donor));
    }

    private Donor findEntity(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found with id: " + id));
    }

    private DonorDto toDto(Donor donor) {
        DonorDto dto = new DonorDto();
        dto.setId(donor.getId());
        dto.setFullName(donor.getFullName());
        dto.setBloodGroup(donor.getBloodGroup());
        dto.setDateOfBirth(donor.getDateOfBirth());
        dto.setAddress(donor.getAddress());
        dto.setHealthScreeningCleared(donor.isHealthScreeningCleared());
        dto.setActive(donor.isActive());
        if (donor.getUser() != null) {
            dto.setEmail(donor.getUser().getEmail());
            dto.setPhone(donor.getUser().getPhone());
        }
        return dto;
    }
}
