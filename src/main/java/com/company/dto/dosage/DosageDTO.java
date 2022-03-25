package com.company.dto.dosage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DosageDTO {
    private int id;
    private String dosageName;
    public DosageDTO() {

    }
    public DosageDTO(String dosageName){
        this.dosageName = dosageName;
    }
}
