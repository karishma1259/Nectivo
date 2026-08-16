package com.nectivo.serviceimpl;
import com.nectivo.dto.GrowthRecordDto;
import com.nectivo.entity.Baby;
import com.nectivo.entity.GrowthRecord;
import com.nectivo.exception.ResourceNotFoundException;
import com.nectivo.repository.BabyRepository;
import com.nectivo.repository.GrowthRecordRepository;
import com.nectivo.service.GrowthRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class GrowthRecordServiceImpl implements GrowthRecordService {
    private final GrowthRecordRepository growthRecordRepository;
    private final BabyRepository babyRepository;

    @Override
    public GrowthRecordDto addRecord(GrowthRecordDto dto) {
        Baby baby = babyRepository.findById(dto.getBabyId())
                .orElseThrow(() -> new ResourceNotFoundException("Baby not found with id: " + dto.getBabyId()));

        GrowthRecord record = GrowthRecord.builder()
                .baby(baby)
                .recordDate(dto.getRecordDate())
                .weightKg(dto.getWeightKg())
                .heightCm(dto.getHeightCm())
                .notes(dto.getNotes())
                .build();

        return toDto(growthRecordRepository.save(record));
    }

    @Override
    public List<GrowthRecordDto> getRecordsForBaby(Long babyId) {
        return growthRecordRepository.findByBabyIdOrderByRecordDateAsc(babyId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteRecord(Long id) {
        growthRecordRepository.deleteById(id);
    }

    private GrowthRecordDto toDto(GrowthRecord record) {
        GrowthRecordDto dto = new GrowthRecordDto();
        dto.setId(record.getId());
        dto.setBabyId(record.getBaby().getId());
        dto.setRecordDate(record.getRecordDate());
        dto.setWeightKg(record.getWeightKg());
        dto.setHeightCm(record.getHeightCm());
        dto.setNotes(record.getNotes());
        return dto;
    }
}