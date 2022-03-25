package com.company.controller;

import com.company.dto.treatment.TreatmentDTO;
import com.company.enums.ProfileRole;
import com.company.service.TreatmentService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/treatment")
public class TreatmentController {
    @Autowired
    private TreatmentService treatmentService;
    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody TreatmentDTO treatmentDTO, HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        return  ResponseEntity.ok(treatmentService.create(treatmentDTO));
    }
    @GetMapping("/get/ALL")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        return ResponseEntity.ok(treatmentService.getAllTreatment());
    }
}
