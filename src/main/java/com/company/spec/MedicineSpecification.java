package com.company.spec;

import com.company.entity.DosageEntity;
import com.company.entity.FormEntity;
import com.company.entity.MedicineEntity;
import com.company.entity.TreatmentEntity;
import org.springframework.data.jpa.domain.Specification;

public class MedicineSpecification {
    public static Specification<MedicineEntity> medicineName (String medicineName) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("medicineName"), medicineName));
    }
    public static Specification<MedicineEntity> treatment (TreatmentEntity treatmentEntity) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("treatment"), treatmentEntity));
    }
    public static Specification<MedicineEntity> form (FormEntity form) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("form"), form));
    }
    public static Specification<MedicineEntity> dosage (DosageEntity dosage) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("dosage"), dosage));
    }
    public static Specification<MedicineEntity> nonPrice (double nonPrice) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("nonPrice"), nonPrice));
    }
}
