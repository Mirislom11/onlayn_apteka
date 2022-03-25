package com.company.controller;

import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.ProfileJwtDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.service.ProfileService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/create/for-admin")
    public ResponseEntity<?> createAdminOrDelivery(@Valid @RequestBody ProfileDTO profileDTO, HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE);
        ProfileDTO response = profileService.sendMessageAndSaveProfile(profileDTO, profileDTO.getRole());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get/ALL")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        return ResponseEntity.ok(profileService.getAllProfile());
    }
    @GetMapping("/get/USERS")
    public ResponseEntity<?> getAllUser(HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.DELIVERY_ROLE, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        return ResponseEntity.ok(profileService.getAllUser());
    }

    @DeleteMapping("/delete/my-account")
    public ResponseEntity<?> deleteMyAccount(HttpServletRequest request) {
        ProfileJwtDTO profileJwtDTO = JwtUtil.getProfile(request);
        String response = profileService.deleteMyAccount(profileJwtDTO.getId());
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete/employees-by-login/{login}")
    public ResponseEntity<?> deleteEmployee (@PathVariable("login") String login, HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE);
        String response = profileService.deleteEmployeeByLogin(login);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/update-my-account")
    public ResponseEntity<?> updateMyAccount(HttpServletRequest request, @RequestBody ProfileDTO profileDTO) {
        ProfileJwtDTO profileJwtDTO = JwtUtil.getProfile(request);
        String response = profileService.updateMyProfile(profileJwtDTO.getId(), profileDTO);
        return ResponseEntity.ok(response);
    }


}
