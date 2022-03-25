package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "attach")
public class AttachEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;
    @Column(name = "key", length = 52)
    private String key;
    @Column(name = "originalName")
    private String originalName;
    @Column(name = "path")
    private String path;
    @Column(name = "size")
    private Long size;
    @Column(name = "extension")
    private String extension;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
