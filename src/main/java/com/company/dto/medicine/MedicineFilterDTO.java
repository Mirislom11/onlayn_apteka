package com.company.dto.medicine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicineFilterDTO {
    private String medicineName;
    private String formName;
    private String treatmentName;
    private String dosageName;
    private Double nonPrice;
}
