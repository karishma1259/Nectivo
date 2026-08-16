package com.nectivo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "babies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Baby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String babyName;

    private LocalDate dateOfBirth;

    private Double birthWeightKg;

    @Column(nullable = false)
    private String parentName;

    @Column(nullable = false)
    private String parentContact;

    @Builder.Default
    private boolean nicuAdmitted = false;

    private String diagnosisNotes;
}
