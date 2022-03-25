package com.company.service;

import com.company.dto.ApiResponse;
import com.company.entity.IncomeMedicineEntity;
import com.company.entity.MedicineEntity;
import com.company.repository.IncomeMedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class IncomeMedicineService {
    @Autowired
    private IncomeMedicineRepository incomeMedicineRepository;

    public ApiResponse create (int count, MedicineEntity medicine, LocalDate expiredDate) {
        IncomeMedicineEntity incomeMedicineEntity = new IncomeMedicineEntity();
        incomeMedicineEntity.setMedicine(medicine);
        incomeMedicineEntity.setCount(count);
        incomeMedicineEntity.setExpiredDate(expiredDate);
        incomeMedicineEntity.setCreatedDateTime(LocalDateTime.now());
        incomeMedicineRepository.save(incomeMedicineEntity);
        return new ApiResponse("Successfully saved Incoming medicine", true);
    }

    public ApiResponse delete(MedicineEntity medicine) {
        incomeMedicineRepository.deleteByMedicine(medicine);
        return new ApiResponse("Successfully delete from income medicine", true);
    }
}
