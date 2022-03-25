package com.company.entity;

import com.company.enums.CompanyStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "suppliers")
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "company_name", nullable = false, unique = true, length = 54)
    private String companyName;
    @Column(name = "email", length = 32, unique = true)
    private String email;
    @Column(name = "phone", length = 32, unique = true)
    private String phone;
    @Column(name = "description", length = 1024)
    private String description;
    @Column(name = "created_date")
    private LocalDateTime createdDateTime;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private CompanyStatus status;
    @JoinColumn(name = "address_id")
    @OneToOne(fetch =  FetchType.EAGER, cascade = CascadeType.ALL)
    private AddressEntity address;
}
