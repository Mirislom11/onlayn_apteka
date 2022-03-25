package com.company.controller;

import com.company.dto.dosage.DosageDTO;
import com.company.dto.form.FormDTO;
import com.company.dto.medicine.MedicineDTO;
import com.company.dto.medicine.MedicineFilterDTO;
import com.company.dto.treatment.TreatmentDTO;
import com.company.entity.DosageEntity;
import com.company.entity.FormEntity;
import com.company.entity.PrescriptionEntity;
import com.company.entity.TreatmentEntity;
import com.company.enums.ProfileRole;
import com.company.repository.MedicineRepository;
import com.company.service.*;
import com.company.util.JwtUtil;
import io.jsonwebtoken.Jwt;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
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
