package com.nectivo.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
@Entity
@Table(name = "growth_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrowthRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "baby_id", nullable = false)
    private Baby baby;
    @Column(nullable = false)
    private LocalDate recordDate;
    @Column(nullable = false)
    private Double weightKg;
    private Double heightCm;
    private String notes;
}