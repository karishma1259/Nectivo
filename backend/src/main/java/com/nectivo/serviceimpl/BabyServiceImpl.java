package com.nectivo.serviceimpl;

import com.nectivo.dto.BabyDto;
import com.nectivo.entity.Baby;
import com.nectivo.exception.ResourceNotFoundException;
import com.nectivo.repository.BabyRepository;
import com.nectivo.service.BabyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BabyServiceImpl implements BabyService {

    private final BabyRepository babyRepository;

    @Override
    public BabyDto createBaby(BabyDto dto) {
        Baby baby = Baby.builder()
                .babyName(dto.getBabyName())
                .dateOfBirth(dto.getDateOfBirth())
                .birthWeightKg(dto.getBirthWeightKg())
                .parentName(dto.getParentName())
                .parentContact(dto.getParentContact())
                .nicuAdmitted(dto.isNicuAdmitted())
                .diagnosisNotes(dto.getDiagnosisNotes())
                .build();

        return toDto(babyRepository.save(baby));
    }

    @Override
    public BabyDto updateBaby(Long id, BabyDto dto) {
        Baby baby = findEntity(id);
        baby.setBabyName(dto.getBabyName());
        baby.setDateOfBirth(dto.getDateOfBirth());
        baby.setBirthWeightKg(dto.getBirthWeightKg());
        baby.setParentName(dto.getParentName());
        baby.setParentContact(dto.getParentContact());
        baby.setNicuAdmitted(dto.isNicuAdmitted());
        baby.setDiagnosisNotes(dto.getDiagnosisNotes());
        return toDto(babyRepository.save(baby));
    }

    @Override
    public BabyDto getBaby(Long id) {
        return toDto(findEntity(id));
    }

    @Override
    public List<BabyDto> getAllBabies() {
        return babyRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteBaby(Long id) {
        babyRepository.delete(findEntity(id));
    }

    private Baby findEntity(Long id) {
        return babyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Baby not found with id: " + id));
    }

    private BabyDto toDto(Baby baby) {
        BabyDto dto = new BabyDto();
        dto.setId(baby.getId());
        dto.setBabyName(baby.getBabyName());
        dto.setDateOfBirth(baby.getDateOfBirth());
        dto.setBirthWeightKg(baby.getBirthWeightKg());
        dto.setParentName(baby.getParentName());
        dto.setParentContact(baby.getParentContact());
        dto.setNicuAdmitted(baby.isNicuAdmitted());
        dto.setDiagnosisNotes(baby.getDiagnosisNotes());
        return dto;
    }
}
