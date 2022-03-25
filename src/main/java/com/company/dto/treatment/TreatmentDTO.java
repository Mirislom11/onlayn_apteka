package com.company.dto.treatment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreatmentDTO {
    private int id;
    private String disease;
    public TreatmentDTO (){

    }
    public TreatmentDTO (String disease){
        this.disease = disease;
    }
}
