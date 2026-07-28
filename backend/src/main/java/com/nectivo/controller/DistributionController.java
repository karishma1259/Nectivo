package com.nectivo.controller;

import com.nectivo.dto.DistributionDto;
import com.nectivo.service.DistributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/distribution")
@RequiredArgsConstructor
public class DistributionController {

    private final DistributionService distributionService;

    @PostMapping
    public ResponseEntity<DistributionDto> distribute(@RequestBody DistributionDto dto) {
        return ResponseEntity.ok(distributionService.distributeBottle(dto));
    }

    @GetMapping
    public ResponseEntity<List<DistributionDto>> getAll() {
        return ResponseEntity.ok(distributionService.getAllDistributions());
    }
}
