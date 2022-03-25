package com.company.controller;

import com.company.dto.prescription.PrescriptionDTO;
import com.company.enums.ProfileRole;
import com.company.service.PrescriptionService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/prescription")
public class PrescriptionController {
    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody PrescriptionDTO prescriptionDTO, HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        return ResponseEntity.ok(prescriptionService.create(prescriptionDTO));
    }
    @GetMapping("/get/All")
    public ResponseEntity<?> getAll (HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        return ResponseEntity.ok(prescriptionService.getAllPrescription());
    }
}
