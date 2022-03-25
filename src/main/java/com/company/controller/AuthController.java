package com.company.controller;

import com.company.dto.AuthDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.ProfileJwtDTO;
import com.company.enums.ProfileRole;
import com.company.enums.VerificationRole;
import com.company.service.AuthService;
import com.company.service.ProfileService;
import com.company.service.SupplierService;
import com.company.util.JwtUtil;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private SupplierService supplierService;
    @PostMapping("/authorization")
    public ResponseEntity<?> authorization (@Valid @RequestBody AuthDTO authDTO) {
        return ResponseEntity.ok(authService.authorization(authDTO));
    }
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody ProfileDTO profileDTO){
        ProfileDTO response = authService.registration(profileDTO);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/verification/PROFILE_ROLE/{jwt}")
    public ResponseEntity<?> verification (@PathVariable("jwt") String jwt) {
        int id = JwtUtil.getIdForRegistration(jwt);
        String response = profileService.changeProfileToCreated(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/verification/SUPPLIER_ROLE/{jwt}")
    public ResponseEntity<?> verificationSupplier(@PathVariable("jwt") String jwt) {
        int id = JwtUtil.getIdForRegistration(jwt);
        String response = supplierService.changeSupplierStatus(id);
        return ResponseEntity.ok(response);
    }
}
