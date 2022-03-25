package com.company.service;

import com.company.dto.ApiResponse;
import com.company.dto.medicine.MedicineDTO;
import com.company.dto.medicine.MedicineFilterDTO;
import com.company.dto.prescription.PrescriptionDTO;
import com.company.entity.*;
import com.company.exception.ItemNotFoundException;
import com.company.repository.MedicineRepository;
import com.company.spec.MedicineSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicineService {
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @Autowired
    private IncomeMedicineService incomeMedicineService;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private DosageService dosageService;
    @Autowired
    private TreatmentService treatmentService;
    @Autowired
    private PrescriptionService prescriptionService;
    @Autowired
    private FormService formService;
    @Autowired
    private SupplierService supplierService;
    public MedicineDTO create (MedicineDTO medicineDTO){
        MedicineEntity currentMedicine = getMedicineEntityByName(medicineDTO.getMedicineName());
        if (currentMedicine != null && currentMedicine.getDosage().getDosageName().equals(medicineDTO.getDosageName())){
            int count = currentMedicine.getCount();
            count += medicineDTO.getCount();
            currentMedicine.setCount(count);
            currentMedicine.setNonPrice(medicineDTO.getNonPrice());
            currentMedicine.setCostPrice(medicineDTO.getCostPrice());
            medicineRepository.save(currentMedicine);
            incomeMedicineService.create(medicineDTO.getCount(), currentMedicine, LocalDate.parse(medicineDTO.getExpiredDate(), format));
            return toDTO(currentMedicine);
        }
        MedicineEntity medicine = toEntity(medicineDTO);
        medicineRepository.save(medicine);
        incomeMedicineService.create(medicine.getCount(), medicine, LocalDate.parse(medicineDTO.getExpiredDate(), format));
        medicineDTO.setId(medicine.getId());
        return medicineDTO;
    }

    public PageImpl<MedicineDTO> filterSpec (int page, int size, MedicineFilterDTO medicineFilterDTO) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Specification<MedicineEntity> spec = null;
        if (medicineFilterDTO.getTreatmentName() != null) {
            TreatmentEntity treatment = treatmentService.getByDisease(medicineFilterDTO.getTreatmentName());
            spec =Specification.where(MedicineSpecification.treatment(treatment));
        }else {
            TreatmentEntity treatment = treatmentService.getByDisease("antibiotics");
            spec =Specification.where(MedicineSpecification.treatment(treatment));
        }
        if (medicineFilterDTO.getMedicineName() != null) {
            spec = spec.and(MedicineSpecification.medicineName(medicineFilterDTO.getMedicineName()));

        }
        if (medicineFilterDTO.getDosageName() != null) {
            DosageEntity dosage = dosageService.getByName(medicineFilterDTO.getDosageName());
            spec = spec.and(MedicineSpecification.dosage(dosage));
        }
        if (medicineFilterDTO.getFormName() != null) {
            FormEntity form = formService.getByName(medicineFilterDTO.getFormName());
            spec = spec.and(MedicineSpecification.form(form));
        }
        if (medicineFilterDTO.getNonPrice() != null) {
            spec = spec.and(MedicineSpecification.nonPrice(medicineFilterDTO.getNonPrice()));
        }
        Page<MedicineEntity> entityPage = medicineRepository.findAll(spec, pageable);
        List<MedicineEntity> medicineEntityList = entityPage.getContent();
        List<MedicineDTO> medicineDTOList = medicineEntityList
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(medicineDTOList, pageable, entityPage.getTotalElements());
    }

    private MedicineEntity toEntity (MedicineDTO medicineDTO) {
        DosageEntity dosageEntity = dosageService.save(medicineDTO.getDosageName());
        TreatmentEntity treatment = treatmentService.save(medicineDTO.getTreatmentName());
        FormEntity formEntity = formService.save(medicineDTO.getFormName());
        List<PrescriptionEntity> prescriptionEntityList = new LinkedList<>();
        for (PrescriptionDTO prescriptionDTO : medicineDTO.getPrescriptionDTOList()) {
            prescriptionEntityList.add(prescriptionService.save(prescriptionDTO.getPrescriptionName()));
        }
        MedicineEntity medicine = new MedicineEntity();
        medicine.setMedicineName(medicineDTO.getMedicineName());
        medicine.setCount(medicineDTO.getCount());
        medicine.setCostPrice(medicineDTO.getCostPrice());
        medicine.setNonPrice(medicineDTO.getNonPrice());
        medicine.setInstruction(medicineDTO.getInstruction());
        medicine.setForm(formEntity);
        medicine.setDosage(dosageEntity);
        medicine.setPrescriptionEntityList(prescriptionEntityList);
        medicine.setTreatment(treatment);
        medicine.setSupplier(supplierService.getSupplierEntityByCompanyName(medicineDTO.getSupplierName()));
        return medicine;
    }
    public MedicineDTO getByName (String medicineName) {
        if (getMedicineEntityByName(medicineName) != null) {
            return toDTO(Objects.requireNonNull(getMedicineEntityByName(medicineName)));
        }
        throw new ItemNotFoundException("Medicine by this name not found");
    }
    public ApiResponse update (int id, MedicineDTO medicineDTO) {
        MedicineEntity medicine = get(id);
        if (medicineDTO.getMedicineName() != null) {
            medicine.setMedicineName(medicineDTO.getMedicineName());
        }
        medicineRepository.save(medicine);
        return new ApiResponse("Successfully updated", true);
    }
    public ApiResponse delete (int id) {
        MedicineEntity medicine = get(id);
        incomeMedicineService.delete(medicine);
        medicineRepository.deleteById(id);
        return new ApiResponse("Successfully deleted", true);
    }



    private MedicineEntity getMedicineEntityByName(String medicineName) {
        Optional<MedicineEntity> optional = medicineRepository.findByMedicineNameLike(medicineName + "%");
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
    public List<MedicineDTO> getAll() {
        List<MedicineEntity> medicineEntityList = medicineRepository.findAll();
        return medicineEntityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private MedicineDTO toDTO (MedicineEntity medicine) {
        MedicineDTO medicineDTO = new MedicineDTO();
        medicineDTO.setId(medicine.getId());
        medicineDTO.setMedicineName(medicine.getMedicineName());
        medicineDTO.setCount(medicine.getCount());
        medicineDTO.setInstruction(medicine.getInstruction());
        medicineDTO.setNonPrice(medicine.getNonPrice());
        medicineDTO.setCostPrice(medicine.getCostPrice());
        medicineDTO.setDosageName(medicine.getDosage().getDosageName());
        medicineDTO.setFormName(medicine.getForm().getFormName());
        medicineDTO.setSupplierName(medicine.getSupplier().getCompanyName());
        medicineDTO.setTreatmentName(medicine.getTreatment().getDisease());
        List<PrescriptionEntity> prescriptionEntityList = medicine.getPrescriptionEntityList();
        List<PrescriptionDTO> prescriptionDTOList = prescriptionEntityList.stream()
                .map(prescriptionEntity -> prescriptionService.toDTO(prescriptionEntity))
                .collect(Collectors.toList());
        medicineDTO.setPrescriptionDTOList(prescriptionDTOList);
        return medicineDTO;
    }
    public MedicineEntity get (int id) {
        Optional<MedicineEntity> optional = medicineRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ItemNotFoundException("Medicine by this id not found");
    }
}
