package com.company.service;

import com.company.dto.form.FormDTO;
import com.company.dto.prescription.PrescriptionDTO;
import com.company.entity.FormEntity;
import com.company.entity.PrescriptionEntity;
import com.company.repository.FormRepository;
import com.company.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormService {
    @Autowired
    private FormRepository formRepository;

    public FormDTO create (FormDTO formDTO) {
        FormEntity entity = save(formDTO.getForm());
        return toDTO(entity);
    }

    public FormEntity save (String formName) {
        FormEntity entity = getByName(formName);
        if (entity != null) {
            return entity;
        }
        FormEntity formEntity = new FormEntity();
        formEntity.setFormName(formName);
        formRepository.save(formEntity);
        return formEntity;
    }
    public List<FormDTO> getAllForm () {
        return formRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    private FormDTO toDTO (FormEntity entity) {
        FormDTO formDTO = new FormDTO();
        formDTO.setId(entity.getId());
        formDTO.setForm(entity.getFormName());
        return formDTO;
    }
    public FormEntity getByName (String formName) {
        Optional<FormEntity> optional =  formRepository.findByFormName(formName);
        return optional.orElse(null);
    }
}
