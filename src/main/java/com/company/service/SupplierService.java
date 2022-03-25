package com.company.service;

import com.company.dto.supplier.SupplierDTO;
import com.company.entity.EmailEntity;
import com.company.entity.SupplierEntity;
import com.company.enums.CompanyStatus;
import com.company.enums.VerificationRole;
import com.company.exception.ItemNotFoundException;
import com.company.repository.SupplierRepository;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierService {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss");
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private EmailServiceImpl emailService;

    public SupplierDTO create(SupplierDTO supplierDTO) {
        SupplierEntity supplier = new SupplierEntity();
        supplier.setCompanyName(supplierDTO.getCompanyName());
        supplier.setEmail(supplierDTO.getEmail());
        supplier.setDescription(supplierDTO.getDescription());
        supplier.setPhone(supplierDTO.getPhone());
        supplier.setCreatedDateTime(LocalDateTime.now());
        supplier.setStatus(CompanyStatus.ACTIVE);
        supplierRepository.save(supplier);
        supplierDTO.setId(supplier.getId());
        supplierDTO.setCreatedDateTime(supplier.getCreatedDateTime().format(formatter));
        StringBuilder body = emailService.createBody(supplier.getId(), VerificationRole.SUPPLIER_ROLE);
        EmailEntity email = emailService.createEmail(supplierDTO.getEmail(), "Registration Apteka uz", body.toString());
        emailService.sendEmail(email);
        return supplierDTO;
    }
    public SupplierDTO getById (int id) {
        Optional<SupplierEntity> optional = supplierRepository.findById(id);
        if (optional.isPresent()) {
            return toDTO(optional.get());
        }
        throw new ItemNotFoundException("Supplier by this id not found");
    }
    public SupplierDTO getByCompanyName(String companyName) {
        return toDTO(getSupplierEntityByCompanyName(companyName));
    }
    public List<SupplierDTO> getAllSuppliers() {
        List<SupplierEntity> supplierEntityList = supplierRepository.findAll();
        return supplierEntityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    public String changeSupplierStatus(int id) {
        SupplierEntity supplier = get(id);
        supplier.setStatus(CompanyStatus.CREATED);
        supplierRepository.save(supplier);
        return "Successfully changed";
    }
    public String update (SupplierDTO supplierDTO, int id) {
        SupplierEntity supplier = get(id);
        if (supplierDTO.getCompanyName() != null) {
            supplier.setCompanyName(supplierDTO.getCompanyName());
        }
        else if (supplierDTO.getPhone() != null) {
            supplier.setPhone(supplierDTO.getPhone());
        }else if (supplierDTO.getDescription() != null) {
            supplier.setDescription(supplierDTO.getDescription());
        }
        supplierRepository.save(supplier);
        return "Successfully update";
    }
    public String delete(int id) {
        supplierRepository.deleteById(id);
        return "Successfully updated";
    }
    public SupplierEntity get (int id) {
        Optional<SupplierEntity> optional = supplierRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ItemNotFoundException("Supplier by this id not found");
    }
    public SupplierEntity getSupplierEntityByCompanyName (String companyName) {
        Optional<SupplierEntity> optional = supplierRepository.findSupplierEntityByCompanyName(companyName);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ItemNotFoundException("Company by this name not found");
    }

    private SupplierDTO toDTO(SupplierEntity supplier) {
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setId(supplier.getId());
        supplierDTO.setDescription(supplier.getDescription());
        supplierDTO.setEmail(supplier.getEmail());
        supplierDTO.setCompanyName(supplier.getCompanyName());
        supplierDTO.setPhone(supplier.getPhone());
        supplierDTO.setCreatedDateTime(supplier.getCreatedDateTime().format(formatter));
        return supplierDTO;
    }

}
