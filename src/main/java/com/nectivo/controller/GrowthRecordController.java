package com.nectivo.controller;
import com.nectivo.dto.GrowthRecordDto;
import com.nectivo.service.GrowthRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/growth-records")
@RequiredArgsConstructor
public class GrowthRecordController {
    private final GrowthRecordService growthRecordService;

    @PostMapping
    public ResponseEntity<GrowthRecordDto> create(@RequestBody GrowthRecordDto dto) {
        return ResponseEntity.ok(growthRecordService.addRecord(dto));
    }

    @GetMapping("/baby/{babyId}")
    public ResponseEntity<List<GrowthRecordDto>> getForBaby(@PathVariable Long babyId) {
        return ResponseEntity.ok(growthRecordService.getRecordsForBaby(babyId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        growthRecordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
}