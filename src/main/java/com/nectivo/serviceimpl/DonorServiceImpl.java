package com.nectivo.serviceimpl;
import com.nectivo.dto.DonorDto;
import com.nectivo.entity.Donor;
import com.nectivo.entity.User;
import com.nectivo.exception.ResourceNotFoundException;
import com.nectivo.repository.DonorRepository;
import com.nectivo.repository.UserRepository;
import com.nectivo.service.DonorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class DonorServiceImpl implements DonorService {
    private final DonorRepository donorRepository;
    private final UserRepository userRepository;

    @Override
    public DonorDto createDonor(DonorDto dto) {
        Donor.DonorBuilder builder = Donor.builder()
                .fullName(dto.getFullName())
                .bloodGroup(dto.getBloodGroup())
                .dateOfBirth(dto.getDateOfBirth())
                .address(dto.getAddress())
                .healthScreeningCleared(false)
                .active(true);

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            userRepository.findByEmail(dto.getEmail()).ifPresent(builder::user);
        }

        return toDto(donorRepository.save(builder.build()));
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

    @Override
    public DonorDto getMyDonor(String email) {
        Donor donor = donorRepository.findByUser_Email(email)
                .orElseThrow(() -> new ResourceNotFoundException("No donor profile linked to this account yet. Ask staff to link your donor record."));
        return toDto(donor);
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