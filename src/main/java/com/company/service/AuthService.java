package com.company.service;

import com.company.dto.AuthDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.enums.ProfileRole;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private ProfileService profileService;
    public ProfileDTO authorization (AuthDTO authDTO) {
        ProfileDTO profileDTO = profileService.getProfileDTOByLoginAndPassword(authDTO.getLogin(), authDTO.getPassword());
        profileDTO.setJwt(JwtUtil.createJwt(profileDTO.getId(), profileDTO.getRole()));
        return profileDTO;
    }
    public ProfileDTO registration (ProfileDTO profileDTO) {
        ProfileDTO response = profileService.sendMessageAndSaveProfile(profileDTO, ProfileRole.USER_ROLE);
        return response;
    }
}
