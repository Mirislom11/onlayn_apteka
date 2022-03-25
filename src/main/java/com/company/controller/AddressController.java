package com.company.controller;

import com.company.dto.address.AddressDTO;
import com.company.dto.address.AddressFilterDTO;
import com.company.dto.profile.ProfileJwtDTO;
import com.company.entity.AddressEntity;
import com.company.enums.ProfileRole;
import com.company.service.AddressService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody AddressDTO address, HttpServletRequest request) {
        ProfileJwtDTO profileJwtDTO = JwtUtil.getProfile(request);
        return ResponseEntity.ok(addressService.create(address, profileJwtDTO.getId()));
    }
    @PostMapping("/create-for-supplier")
    public ResponseEntity<?> createForSupplier(@RequestBody AddressDTO addressDTO, HttpServletRequest request){
        JwtUtil.getProfile(request, ProfileRole.MANAGER_ROLE, ProfileRole.ADMIN_ROLE);
        AddressDTO response = addressService.create(addressDTO);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-myAddress")
    public ResponseEntity<?> getMyAddress(HttpServletRequest request) {
        ProfileJwtDTO profileJwtDTO = JwtUtil.getProfile(request);
        return ResponseEntity.ok(addressService.get(profileJwtDTO.getId()));
    }
    @GetMapping("/get/ALL")
    public ResponseEntity<?> getAllAddress(HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE, ProfileRole.MANAGER_ROLE);
        return ResponseEntity.ok(addressService.getAllAddress());
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(HttpServletRequest request){
        ProfileJwtDTO profileJwtDTO = JwtUtil.getProfile(request);
        return ResponseEntity.ok(addressService.deleteAddressByProfileId(profileJwtDTO.getId()));
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody AddressDTO addressDTO) {
        ProfileJwtDTO profileJwtDTO = JwtUtil.getProfile(request);
        String response = addressService.update(addressDTO, profileJwtDTO.getId());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-by-country/{country}")
    public ResponseEntity<?> getByCountry(HttpServletRequest request, @PathVariable("country") String country) {
        JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE, ProfileRole.MANAGER_ROLE);
        List<AddressDTO> addressDTOList = addressService.getAllAddressByCountry(country);
        return ResponseEntity.ok(addressDTOList);
    }
    @PostMapping("/filter")
    public ResponseEntity<?> filterAddress(@RequestBody AddressFilterDTO addressFilterDTO, @RequestParam("page") int page,
                                           @RequestParam("size") int size) {
        return ResponseEntity.ok(addressService.filterSpec(page, size, addressFilterDTO));
    }

}
