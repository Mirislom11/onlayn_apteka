package com.company.service;

import com.company.dto.profile.ProfileDTO;
import com.company.entity.AddressEntity;
import com.company.entity.EmailEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.enums.VerificationRole;
import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss");
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailServiceImpl emailService;
    public ProfileDTO getProfileDTOByLoginAndPassword(String login, String password) {
         Optional<ProfileEntity> optional = profileRepository.findByLoginAndPassword(login, password);
        if (optional.isPresent()) {
            return  toDTO(optional.get());
        }
        throw new  ItemNotFoundException("profile by this login and password not found");
    }

    public ProfileDTO sendMessageAndSaveProfile(ProfileDTO profileDTO, ProfileRole role) {
        ProfileEntity entity = createProfile(profileDTO, role);
        profileRepository.save(entity);
        profileDTO.setId(entity.getId());
        profileDTO.setCreatedDateTime(entity.getCreatedDateTime().format(formatter));
        StringBuilder stringBuilder = emailService.createBody(profileDTO.getId(), VerificationRole.PROFILE_ROLE);
        EmailEntity email = emailService.createEmail(profileDTO.getEmail(), "Registration Apteka Uz Test", stringBuilder.toString());
        emailService.sendEmail(email);
        return profileDTO;
    }
    public String changeProfileToCreated(int id) {
        ProfileEntity profile = get(id);
        profile.setStatus(ProfileStatus.CREATED);
        profileRepository.save(profile);
        return "Successfully verification";
    }
    public List<ProfileDTO> getAllProfile() {
        List<ProfileEntity> profileEntityList = profileRepository.findAll();
        return toDTOList(profileEntityList);
    }
    public String deleteMyAccount (int id) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isPresent()) {
            profileRepository.deleteById(id);
            return "Successfully deleted";
        }
        throw new ItemNotFoundException("Profile by this id not found");
    }
    public String deleteEmployeeByLogin(String login){
        Optional<ProfileEntity> optional = profileRepository.findByLogin(login);
        if (optional.isPresent()) {
            if (optional.get().getRole().equals(ProfileRole.USER_ROLE)) {
                throw new BadRequestException("You can not delete user");
            }
        }
        profileRepository.deleteByLogin(login);
        return "Successfully delete";
    }
    public AddressEntity getAddressEntityByProfileId(int profileId) {
        ProfileEntity profile = get(profileId);
        return profile.getAddress();
    }
    public String updateMyProfile (int id, ProfileDTO profileDTO) {
        ProfileEntity profile = get(id);
        if (!Integer.valueOf(profile.getId()).equals(id)) {
            throw new BadRequestException("You not owner this account");
        }
        if (profileDTO.getEmail() != null) {
            profile.setEmail(profileDTO.getEmail());
        }else if (profileDTO.getName() != null) {
            profile.setName(profileDTO.getName());
        }else if (profileDTO.getPassword() != null) {
            profile.setPassword(profileDTO.getPassword());
        }else if(profileDTO.getPhone() != null) {
            profile.setPhone(profileDTO.getPhone());
        }else if (profileDTO.getSurname() != null) {
            profile.setSurname(profileDTO.getSurname());
        }else if (profileDTO.getLogin() != null) {
            profile.setLogin(profileDTO.getLogin());
        }
        profileRepository.save(profile);
        return "Successfully updated";
    }
    public List<ProfileDTO> getAllUser() {
        List<ProfileEntity>profileEntityList = profileRepository.findAllByRole(ProfileRole.USER_ROLE);
        return toDTOList(profileEntityList);
    }

    public ProfileEntity get (int id) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ItemNotFoundException("Profile by this id not found");
    }

    private ProfileDTO  toDTO(ProfileEntity profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profile.getId());
        profileDTO.setName(profile.getName());
        profileDTO.setSurname(profile.getSurname());
        profileDTO.setEmail(profile.getEmail());
        profileDTO.setLogin(profile.getLogin());
        profileDTO.setPassword(profile.getPassword());
        profileDTO.setCreatedDateTime(profile.getCreatedDateTime().format(formatter));
        profileDTO.setRole(profile.getRole());
        return profileDTO;
    }
    private ProfileEntity createProfile(ProfileDTO profileDTO, ProfileRole role) {
        ProfileEntity entity = new ProfileEntity();
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setLogin(profileDTO.getLogin());
        entity.setPassword(profileDTO.getPassword());
        entity.setEmail(profileDTO.getEmail());
        entity.setName(profileDTO.getName());
        entity.setSurname(profileDTO.getSurname());
        entity.setCreatedDateTime(LocalDateTime.now());
        entity.setRole(role);
        entity.setPhone(profileDTO.getPhone());
        return entity;
    }
    private List<ProfileDTO> toDTOList(List<ProfileEntity> profileEntityList) {
        return profileEntityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
