package com.company.service;

import com.company.dto.prescription.PrescriptionDTO;
import com.company.entity.PrescriptionEntity;
import com.company.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    public PrescriptionDTO create(PrescriptionDTO prescriptionDTO) {
        return toDTO(save(prescriptionDTO.getPrescriptionName()));
    }
    public PrescriptionEntity save (String prescriptionName) {
        PrescriptionEntity prescriptionEntity = findByPrescriptionName(prescriptionName);
        if (prescriptionEntity == null) {
            PrescriptionEntity entity = new PrescriptionEntity();
            entity.setPrescriptionName(prescriptionName);
            prescriptionRepository.save(entity);
            return entity;
        }
        return prescriptionEntity;
    }

    public List<PrescriptionDTO> getAllPrescription() {
        return prescriptionRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private PrescriptionEntity findByPrescriptionName (String name) {
        Optional<PrescriptionEntity> optional = prescriptionRepository.findByPrescriptionName(name);
        return optional.orElse(null);
    }
    public PrescriptionDTO toDTO (PrescriptionEntity prescriptionEntity) {
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setId(prescriptionEntity.getId());
        prescriptionDTO.setPrescriptionName(prescriptionEntity.getPrescriptionName());
        return prescriptionDTO;
    }
}
