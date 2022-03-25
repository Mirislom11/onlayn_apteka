package com.company.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressFilterDTO {
    private String country;
    private String city;
    private String area;
    private String street;
    private String houseNumber;
}
