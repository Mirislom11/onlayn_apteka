package com.company.dto.prescription;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PrescriptionDTO {

    private int id;
    @NotEmpty
    private String prescriptionName;
}
