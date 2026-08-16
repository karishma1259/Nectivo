package com.nectivo.controller;
import com.nectivo.dto.DonorDto;
import com.nectivo.service.DonorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/donors")
@RequiredArgsConstructor
public class DonorController {
    private final DonorService donorService;
    @PostMapping
    public ResponseEntity<DonorDto> create(@RequestBody DonorDto dto) {
        return ResponseEntity.ok(donorService.createDonor(dto));
    }
    @GetMapping
    public ResponseEntity<List<DonorDto>> getAll() {
        return ResponseEntity.ok(donorService.getAllDonors());
    }
    @GetMapping("/me")
    public ResponseEntity<DonorDto> getMine(Authentication authentication) {
        return ResponseEntity.ok(donorService.getMyDonor(authentication.getName()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<DonorDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(donorService.getDonor(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<DonorDto> update(@PathVariable Long id, @RequestBody DonorDto dto) {
        return ResponseEntity.ok(donorService.updateDonor(id, dto));
    }
    @PatchMapping("/{id}/clear-screening")
    public ResponseEntity<DonorDto> clearScreening(@PathVariable Long id) {
        return ResponseEntity.ok(donorService.clearHealthScreening(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        donorService.deleteDonor(id);
        return ResponseEntity.noContent().build();
    }
}