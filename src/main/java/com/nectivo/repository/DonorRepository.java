package com.nectivo.repository;
import com.nectivo.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface DonorRepository extends JpaRepository<Donor, Long> {
    Optional<Donor> findByUser_Email(String email);
}