package com.nectivo.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDto {
    private long totalDonors;
    private long totalBabies;
    private long totalDonations;
    private long availableBottles;
    private long expiredBottles;
    private long distributedBottles;
    private double totalMilkCollectedMl;
    private double totalMilkDistributedMl;

    // New: role-wise user counts
    private long totalDoctors;
    private long totalStaff;
    private long totalRegisteredDonorUsers;
    private long totalAdmins;
}