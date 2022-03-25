package com.company.dto.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {
    private int id;
    @NotEmpty
    private String companyName;
    @Email
    private String email;
    @NotEmpty
    private String phone;
    private String description;
    private String createdDateTime;
}
