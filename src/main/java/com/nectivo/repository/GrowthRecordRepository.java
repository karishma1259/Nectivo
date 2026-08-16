package com.nectivo.repository;
import com.nectivo.entity.GrowthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface GrowthRecordRepository extends JpaRepository<GrowthRecord, Long> {
    List<GrowthRecord> findByBabyIdOrderByRecordDateAsc(Long babyId);
}
