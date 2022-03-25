package com.company.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "country", nullable = false, length = 16)
    private String country;
    @Column(name = "city", nullable = false, length = 16)
    private String city;
    @Column(name = "area", nullable = false, length = 32)
    private String area;
    @Column(name = "street")
    private String street;
    @Column(name = "house_number")
    private String houseNumber;
    @OneToOne(mappedBy = "address")
    private ProfileEntity profile;
}
