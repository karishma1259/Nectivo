package com.nectivo.controller;

import com.nectivo.dto.DonationDto;
import com.nectivo.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;

    @PostMapping
    public ResponseEntity<DonationDto> create(@RequestBody DonationDto dto) {
        return ResponseEntity.ok(donationService.createDonation(dto));
    }

    @GetMapping
    public ResponseEntity<List<DonationDto>> getAll() {
        return ResponseEntity.ok(donationService.getAllDonations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(donationService.getDonation(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DonationDto> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(donationService.updateStatus(id, status));
    }
}
