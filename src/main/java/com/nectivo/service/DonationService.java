package com.nectivo.service;
import com.nectivo.dto.DonationDto;
import java.util.List;
public interface DonationService {
    DonationDto createDonation(DonationDto dto);
    DonationDto updateStatus(Long id, String status);
    DonationDto getDonation(Long id);
    List<DonationDto> getAllDonations();
    List<DonationDto> getMyDonations(String email);
}