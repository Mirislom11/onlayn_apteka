package com.company.entity;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false, length = 32)
    private String name;
    @Column(name = "surname", nullable = false, length = 32)
    private String surname;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "login", nullable = false, unique = true, length = 32)
    private String login;
    @Column(name = "phone", nullable = false, length = 32, unique = true)
    private String phone;
    @Column(name = "password" /*nullable = false, length = 32*/)
    private String password;
    @Column(name = "created_date")
    private LocalDateTime createdDateTime;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ProfileRole role;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private AddressEntity address;
}
