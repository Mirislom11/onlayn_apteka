package com.company.controller;

import com.company.dto.form.FormDTO;
import com.company.enums.ProfileRole;
import com.company.service.FormService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/form")
public class FormController {
    @Autowired
    private FormService formService;

    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody FormDTO formDTO, HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        return ResponseEntity.ok(formService.create(formDTO));
    }
    @GetMapping("/get/All")
    public ResponseEntity<?> getAll (HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        return ResponseEntity.ok(formService.getAllForm());
    }
}
