package com.company.service;

import com.company.dto.treatment.TreatmentDTO;
import com.company.entity.TreatmentEntity;
import com.company.repository.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TreatmentService {
    @Autowired
    private TreatmentRepository treatmentRepository;

    public TreatmentDTO create(TreatmentDTO treatmentDTO) {
        return toDTO(save(treatmentDTO.getDisease()));
    }
    public TreatmentEntity save (String treatmentName) {
        TreatmentEntity treatment = getByDisease(treatmentName);
        if(treatment == null) {
            TreatmentEntity entity = new TreatmentEntity();
            entity.setDisease(treatmentName);
            treatmentRepository.save(entity);
            entity.setId(entity.getId());
            return entity;
        }
        return treatment;
    }
    public List<TreatmentDTO> getAllTreatment (){
        List<TreatmentEntity> treatmentEntityList = treatmentRepository.findAll();
        return  treatmentEntityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TreatmentEntity getByDisease(String disease) {
        Optional<TreatmentEntity> optional = treatmentRepository.findByDisease(disease);
        return optional.orElse(null);
    }
    private TreatmentDTO toDTO(TreatmentEntity treatment) {
        TreatmentDTO treatmentDTO = new TreatmentDTO();
        treatmentDTO.setId(treatment.getId());
        treatmentDTO.setDisease(treatment.getDisease());
        return treatmentDTO;
    }
}
