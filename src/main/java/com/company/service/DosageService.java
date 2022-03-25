package com.company.service;

import com.company.dto.dosage.DosageDTO;
import com.company.entity.DosageEntity;
import com.company.repository.DosageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DosageService {
    @Autowired
    private DosageRepository dosageRepository;

    public DosageDTO create(DosageDTO dosageDTO) {
        return toDTO(save(dosageDTO.getDosageName()));
    }
    public DosageEntity save (String dosageName) {
        DosageEntity dosageEntity = getByName(dosageName);
        if (dosageEntity == null) {
            DosageEntity entity = new DosageEntity();
            entity.setDosageName(dosageName);
            dosageRepository.save(entity);
            return entity;
        }
        return dosageEntity;
    }
    public List<DosageDTO> getAllDosageList() {
        List<DosageEntity> dosageEntityList = dosageRepository.findAll();
        return dosageEntityList.stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
    }

    ///////
    public String deleteDosage(int id) {
        dosageRepository.deleteById(id);
        return "Successfully deleted";
    }
    public DosageEntity getByName(String dosageName) {
        Optional<DosageEntity> optional = dosageRepository.findByDosageName(dosageName);
        return optional.orElse(null);
    }
    private DosageDTO toDTO(DosageEntity dosageEntity) {
        DosageDTO dosageDTO = new DosageDTO();
        dosageDTO.setId(dosageEntity.getId());
        dosageDTO.setDosageName(dosageEntity.getDosageName());
        return dosageDTO;
    }
}
