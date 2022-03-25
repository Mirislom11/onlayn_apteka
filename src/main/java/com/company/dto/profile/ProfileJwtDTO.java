package com.company.dto.profile;

import com.company.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfileJwtDTO  {
    private int id;
    private ProfileRole role;
}
