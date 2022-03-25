package com.company.dto.medicine;

import com.company.dto.prescription.PrescriptionDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicineDTO {
    private int id;
    @NotEmpty(message = "medicine name can not be empty or null")
    private String medicineName;
    @NotEmpty(message = "form name can not be empty or null")
    private String formName;
    @NotEmpty(message = "treatment name can not be empty or null")
    private String treatmentName;
    @NotEmpty(message = "dosage name can not be empty or null")
    @Size(min = 4, max = 12, message = "dosage name must be min 4 max 12")
    private String dosageName;
    @NotEmpty(message = "supplier name can not be empty or null")
    private String supplierName;
    private List<PrescriptionDTO> prescriptionDTOList;
    @Positive
    private int count;
    @Positive
    private double costPrice;
    @Positive
    private double nonPrice;
    private String instruction;
    private String expiredDate;
}
