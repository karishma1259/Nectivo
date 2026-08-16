package com.nectivo.service;
import com.nectivo.dto.GrowthRecordDto;
import java.util.List;
public interface GrowthRecordService {
    GrowthRecordDto addRecord(GrowthRecordDto dto);
    List<GrowthRecordDto> getRecordsForBaby(Long babyId);
    void deleteRecord(Long id);
}
