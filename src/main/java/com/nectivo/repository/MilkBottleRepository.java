package com.nectivo.repository;

import com.nectivo.entity.MilkBottle;
import com.nectivo.enums.BottleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MilkBottleRepository extends JpaRepository<MilkBottle, Long> {
    List<MilkBottle> findByStatus(BottleStatus status);
    List<MilkBottle> findByExpiryDateBeforeAndStatus(LocalDate date, BottleStatus status);
    long countByStatus(BottleStatus status);
}
