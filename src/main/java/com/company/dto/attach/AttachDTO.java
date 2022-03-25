package com.company.dto.attach;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AttachDTO {
    private String key;
    private String originalName;
    private long size;
    private String path;
    private String extension;
    private LocalDateTime createdDate;
    private String url;
}
