package com.nectivo.controller;

import com.nectivo.dto.MilkBottleDto;
import com.nectivo.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/bottles")
    public ResponseEntity<MilkBottleDto> create(@RequestBody MilkBottleDto dto) {
        return ResponseEntity.ok(inventoryService.createBottleFromDonation(dto));
    }

    @GetMapping("/bottles")
    public ResponseEntity<List<MilkBottleDto>> getAll() {
        return ResponseEntity.ok(inventoryService.getAllBottles());
    }

    @GetMapping("/bottles/available")
    public ResponseEntity<List<MilkBottleDto>> getAvailable() {
        return ResponseEntity.ok(inventoryService.getAvailableBottles());
    }

    @PatchMapping("/bottles/{id}/expire")
    public ResponseEntity<MilkBottleDto> markExpired(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.markExpired(id));
    }
}
