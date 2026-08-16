package com.nectivo.serviceimpl;
import com.nectivo.dto.DashboardStatsDto;
import com.nectivo.entity.Donation;
import com.nectivo.enums.BottleStatus;
import com.nectivo.enums.Role;
import com.nectivo.repository.BabyRepository;
import com.nectivo.repository.DistributionRepository;
import com.nectivo.repository.DonationRepository;
import com.nectivo.repository.DonorRepository;
import com.nectivo.repository.MilkBottleRepository;
import com.nectivo.repository.UserRepository;
import com.nectivo.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final DonorRepository donorRepository;
    private final BabyRepository babyRepository;
    private final DonationRepository donationRepository;
    private final MilkBottleRepository bottleRepository;
    private final DistributionRepository distributionRepository;
    private final UserRepository userRepository;
    @Override
    public DashboardStatsDto getStats() {
        double totalCollected = donationRepository.findAll().stream()
                .mapToDouble(Donation::getQuantityMl).sum();
        double totalDistributed = distributionRepository.findAll().stream()
                .mapToDouble(d -> d.getBottle().getQuantityMl()).sum();
        return DashboardStatsDto.builder()
                .totalDonors(donorRepository.count())
                .totalBabies(babyRepository.count())
                .totalDonations(donationRepository.count())
                .availableBottles(bottleRepository.countByStatus(BottleStatus.AVAILABLE))
                .expiredBottles(bottleRepository.countByStatus(BottleStatus.EXPIRED))
                .distributedBottles(bottleRepository.countByStatus(BottleStatus.DISTRIBUTED))
                .totalMilkCollectedMl(totalCollected)
                .totalMilkDistributedMl(totalDistributed)
                .totalDoctors(userRepository.countByRole(Role.DOCTOR))
                .totalStaff(userRepository.countByRole(Role.STAFF))
                .totalRegisteredDonorUsers(userRepository.countByRole(Role.DONOR))
                .totalAdmins(userRepository.countByRole(Role.ADMIN))
                .build();
    }
}