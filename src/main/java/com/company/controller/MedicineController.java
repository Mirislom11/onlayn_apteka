package com.company.controller;

import com.company.dto.medicine.MedicineDTO;
import com.company.dto.medicine.MedicineFilterDTO;
import com.company.enums.ProfileRole;
import com.company.service.*;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/medicine")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;

    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody MedicineDTO medicineDTO, HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        MedicineDTO response = medicineService.create(medicineDTO);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<?> getByName (@PathVariable("name") String name, HttpServletRequest request){
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        MedicineDTO response = medicineService.getByName(name);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get/ALL")
    public ResponseEntity<?> getAll (HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        List<MedicineDTO> medicineDTOList = medicineService.getAll();
        return ResponseEntity.ok(medicineDTOList);
    }
    @GetMapping("/get/attaches/{medicineId}")
    public ResponseEntity<?> getAttaches (@PathVariable("medicineId") int medicineId, HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        return ResponseEntity.ok(medicineService.getAttaches(medicineId));
    }
    @PostMapping("/filter-spec")
    public ResponseEntity<?> filterSpec (@RequestBody MedicineFilterDTO medicineFilterDTO, @RequestParam("page") int page,
                                         @RequestParam("size") int size, HttpServletRequest request) {
                JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        return ResponseEntity.ok(medicineService.filterSpec(page, size, medicineFilterDTO));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update (@RequestBody MedicineDTO medicineDTO, @PathVariable("id") String id,
                                     HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        return ResponseEntity.ok(medicineService.update(Integer.parseInt(id), medicineDTO));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete (@PathVariable("id") int id, HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        return ResponseEntity.ok(medicineService.delete(id));
    }

}
