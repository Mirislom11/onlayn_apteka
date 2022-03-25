package com.company.controller;

import com.company.dto.supplier.SupplierDTO;
import com.company.enums.ProfileRole;
import com.company.service.SupplierService;
import com.company.util.JwtUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SupplierDTO supplierDTO, HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE, ProfileRole.MANAGER_ROLE);
        SupplierDTO response = supplierService.create(supplierDTO);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id, HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        SupplierDTO response = supplierService.getById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getByCompanyName/{name}")
    public ResponseEntity<?> getByCompanyName(@PathVariable("name")String name, HttpServletRequest request){
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        SupplierDTO supplierDTO = supplierService.getByCompanyName(name);
        return  ResponseEntity.ok(supplierDTO);
    }
    @GetMapping("/get/ALL")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        List<SupplierDTO> supplierDTOList = supplierService.getAllSuppliers();
        return ResponseEntity.ok(supplierDTOList);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody SupplierDTO supplierDTO,
                                    HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        return ResponseEntity.ok(supplierService.update(supplierDTO, id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id, HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        return ResponseEntity.ok(supplierService.delete(id));
    }
}
