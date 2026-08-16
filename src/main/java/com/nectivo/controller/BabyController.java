package com.nectivo.controller;

import com.nectivo.dto.BabyDto;
import com.nectivo.service.BabyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/babies")
@RequiredArgsConstructor
public class BabyController {

    private final BabyService babyService;

    @PostMapping
    public ResponseEntity<BabyDto> create(@Valid @RequestBody BabyDto dto) {
        return ResponseEntity.ok(babyService.createBaby(dto));
    }

    @GetMapping
    public ResponseEntity<List<BabyDto>> getAll() {
        return ResponseEntity.ok(babyService.getAllBabies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BabyDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(babyService.getBaby(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BabyDto> update(@PathVariable Long id, @Valid @RequestBody BabyDto dto) {
        return ResponseEntity.ok(babyService.updateBaby(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        babyService.deleteBaby(id);
        return ResponseEntity.noContent().build();
    }
}
