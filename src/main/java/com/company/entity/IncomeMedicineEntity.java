package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class IncomeMedicineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "arrival_date")
    private LocalDateTime createdDateTime;
    @Column(name = "expired_date")
    private LocalDate expiredDate;
    @Column(name = "count")
    private int count;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "medicine_id")
    private MedicineEntity medicine;
}
