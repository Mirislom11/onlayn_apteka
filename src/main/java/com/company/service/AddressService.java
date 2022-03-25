package com.company.service;

import com.company.dto.address.AddressDTO;
import com.company.dto.address.AddressFilterDTO;
import com.company.entity.AddressEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.SupplierEntity;
import com.company.exception.ItemNotFoundException;
import com.company.repository.AddressRepository;
import com.company.repository.ProfileRepository;
import com.company.repository.SupplierRepository;
import com.company.spec.AddressSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private SupplierRepository supplierRepository;

    public AddressDTO create(AddressDTO addressDTO, int profileId) {
        AddressEntity entity = toEntity(addressDTO);
        ProfileEntity profile = profileService.get(profileId);
        addressRepository.save(entity);
        addressDTO.setId(entity.getId());
        profile.setAddress(entity);
        profileRepository.save(profile);
        addressDTO.setUserId(profileId);
        return addressDTO;
    }
    public AddressDTO create(AddressDTO addressDTO) {
        AddressEntity entity = toEntity(addressDTO);
        addressRepository.save(entity);
        addressDTO.setId(entity.getId());
        SupplierEntity supplier = supplierService.get(addressDTO.getUserId());
        supplier.setAddress(entity);
        supplierRepository.save(supplier);
        return addressDTO;
    }
    public AddressDTO get(int profileId) {
        ProfileEntity profile = profileService.get(profileId);
        Optional<AddressEntity> optional = addressRepository.findAddressEntityByProfile(profile);
        if (optional.isPresent()) {
            return toDTO(optional.get());
        }
        throw new ItemNotFoundException("Your address not found");
    }

    public String deleteAddressByProfileId(int profileId) {
        ProfileEntity profile = profileService.get(profileId);
        AddressEntity address = profile.getAddress();
        profile.setAddress(null);
        profileRepository.save(profile);
        if (address != null) {
            addressRepository.deleteById(address.getId());
            return "Successfully deleted";
        }
        throw new ItemNotFoundException("your address not found");
    }

    public String update(AddressDTO addressDTO, int profileId) {
        AddressEntity address = getAddressEntityByProfileId(profileId);
        if (addressDTO.getArea() != null) {
            address.setArea(addressDTO.getArea());
        } else if (addressDTO.getCity() != null) {
            address.setCity(addressDTO.getCity());
        } else if (addressDTO.getCountry() != null) {
            address.setCountry(addressDTO.getCountry());
        } else if (addressDTO.getHouseNumber() != null) {
            address.setHouseNumber(addressDTO.getHouseNumber());
        } else if (addressDTO.getStreet() != null) {
            address.setStreet(addressDTO.getStreet());
        }
        addressRepository.save(address);
        return "Successfully updated";
    }

    private AddressEntity getAddressEntityByProfileId(int profileId) {
        ProfileEntity profile = profileService.get(profileId);
        Optional<AddressEntity> optional = addressRepository.findAddressEntityByProfile(profile);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ItemNotFoundException("Address not found");
    }

    public List<AddressDTO> getAllAddressByCountry(String country) {
        List<AddressEntity> addressEntityList = addressRepository.findAllByCountry(country);
        return addressEntityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AddressDTO> getAllAddress() {
        List<AddressEntity> addressEntityList = addressRepository.findAll();
        return addressEntityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PageImpl<AddressDTO> filterSpec(int page, int size, AddressFilterDTO addressFilterDTO) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<AddressEntity> specification = null;
        if (addressFilterDTO.getCountry() != null)
            specification = Specification.where(AddressSpecification.secForString("country", addressFilterDTO.getCountry()));
        else
            specification = Specification.where(AddressSpecification.secForString("country", "Uzbekistan"));

        if (addressFilterDTO.getArea() != null)
            specification.and(AddressSpecification.secForString("area", addressFilterDTO.getArea()));

        if (addressFilterDTO.getCity() != null)
            specification.and(AddressSpecification.secForString("city", addressFilterDTO.getCity()));
        if (addressFilterDTO.getStreet() != null)
            specification.and(AddressSpecification.secForString("street", addressFilterDTO.getStreet()));
        if (addressFilterDTO.getHouseNumber() != null)
            specification.and(AddressSpecification.secForString("houseNumber", addressFilterDTO.getHouseNumber()));
        Page<AddressEntity> entityPage = addressRepository.findAll(specification, pageable);
        List<AddressEntity> addressEntityList = entityPage.getContent();
        List<AddressDTO> addressDTOList = addressEntityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(addressDTOList, pageable, entityPage.getTotalElements());
    }

    private AddressDTO toDTO(AddressEntity address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setArea(address.getArea());
        addressDTO.setCity(address.getCity());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setHouseNumber(address.getHouseNumber());
        addressDTO.setUserId(address.getProfile().getId());
        return addressDTO;
    }
    private AddressEntity toEntity(AddressDTO addressDTO) {
        AddressEntity entity = new AddressEntity();
        entity.setCity(addressDTO.getCity());
        entity.setArea(addressDTO.getArea());
        entity.setCountry(addressDTO.getCountry());
        entity.setHouseNumber(addressDTO.getHouseNumber());
        entity.setStreet(addressDTO.getStreet());
        return entity;
    }
}
