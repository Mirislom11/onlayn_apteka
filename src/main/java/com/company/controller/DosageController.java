package com.company.controller;

import com.company.dto.dosage.DosageDTO;
import com.company.enums.ProfileRole;
import com.company.service.DosageService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/dosage")
public class DosageController {
    @Autowired
    private DosageService dosageService;
    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody DosageDTO dosageDTO, HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        return ResponseEntity.ok(dosageService.create(dosageDTO));
    }
    @GetMapping("/get/ALL")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(dosageService.getAllDosageList());
    }
}
