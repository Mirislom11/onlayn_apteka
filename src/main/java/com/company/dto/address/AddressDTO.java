package com.company.dto.address;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {
    private int id;
    @NotEmpty
    private String country;
    @NotEmpty
    private String city;
    private String area;
    private String street;
    private String houseNumber;
    private int userId;

}
