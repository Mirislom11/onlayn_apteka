package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "email")
public class EmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "from_account", nullable = false, length = 32)
    private String fromAccount;
    @Column(name = "to_account", nullable = true, length = 32)
    private String toAccount;
    @Column(name = "subject")
    private String subject;
    @Column(name = "content", length = 512)
    private String content;

}
