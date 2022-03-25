package com.company.dto.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class FormDTO {
    private int id;
    @NotEmpty
    private String form;
    public FormDTO () {

    }
    public FormDTO(String form){
        this.form = form;
    }
}
