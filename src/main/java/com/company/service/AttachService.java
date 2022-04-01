package com.company.service;

import com.company.dto.ApiResponse;
import com.company.dto.attach.AttachDTO;
import com.company.entity.AttachEntity;
import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.spi.ResourceBundleProvider;

@Service
public class AttachService {
    @Autowired
    private AttachRepository attachRepository;
    @Autowired
    private MedicineService medicineService;
    @Value("${attach.upload.folder}")
    private String uploadFolder;
    @Value("${attach.open.url}")
    private String attachOpenUrl;
    public AttachDTO saveFile(MultipartFile file, int medicineId) {
        String filePath = getYmDString();
        String fileName = UUID.randomUUID().toString();
        String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        File folder = new File( uploadFolder + "/" +filePath );

        if (!folder.exists()) {
            folder.mkdirs();
        }
        AttachEntity attachEntity = createAttachEntity(fileName, file.getOriginalFilename(), filePath, extension, file.getSize());
        attachRepository.save(attachEntity);
        medicineService.addAttachToMedicine(attachEntity, medicineId);
        AttachDTO attachDTO = createAttachDTO(attachEntity);
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFolder + "/" + filePath + "/" +fileName + "." +extension); // uploads/casas.jpg
            Files.write(path, bytes);
            return attachDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new BadRequestException("File save failed");
    }
    public Resource load (String fileName) {
        Optional<AttachEntity> optional = attachRepository.findByKey(fileName);
        AttachEntity attachEntity = null;
        if (optional.isPresent()) {
            attachEntity = optional.get();
        }
        try {
            Path path = Paths.get(uploadFolder + "/" + attachEntity.getPath() + "/" + fileName + "." + attachEntity.getExtension());
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        throw new ItemNotFoundException("Image by this fileName not found");
    }

    public byte [] loadAttach (String key) throws IOException{
        Optional<AttachEntity> optional = attachRepository.findByKey(key);
        AttachEntity attachEntity = null;
        if (optional.isPresent()) {
            attachEntity = optional.get();
        }
        byte[] imageByte;
        BufferedImage originalName;
        String filePath = attachEntity.getPath() + "/" + key + "." + attachEntity.getExtension();
        try {
            originalName = ImageIO.read(new File(uploadFolder + "/" + filePath));
            String extension = getExtension(attachEntity.getExtension());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalName, extension, baos);
            imageByte = baos.toByteArray();
            baos.flush();
            baos.close();
        }catch (Exception e){
            return new byte[0];
        }

        return imageByte;
    }
    public ApiResponse delete (String key) {
        Optional<AttachEntity> optional = attachRepository.findByKey(key);
        if (!optional.isPresent()) {
            return new ApiResponse("Delete failed", false);
        }
        String filePath = optional.get().getPath() + "/" +key+ "." + optional.get().getExtension();
        File file = new File(filePath);
        file.delete();
        attachRepository.delete(optional.get());
        return new ApiResponse("Successfully deleted", true);
    }

    private AttachEntity createAttachEntity(String fileName, String originalName,String path, String extension, long size){
        AttachEntity attachEntity = new AttachEntity();
        attachEntity.setPath(path);
        attachEntity.setExtension(extension);
        attachEntity.setKey(fileName);
        attachEntity.setOriginalName(originalName);
        attachEntity.setSize(size);
        attachEntity.setCreatedDate(LocalDateTime.now());
        attachEntity.setUrl(attachOpenUrl + "/" + fileName);
        return attachEntity;
    }

    public AttachDTO createAttachDTO (AttachEntity attachEntity) {
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setKey(attachEntity.getKey());
        attachDTO.setExtension(attachEntity.getExtension());
        attachDTO.setPath(attachEntity.getPath());
        attachDTO.setSize(attachEntity.getSize());
        attachDTO.setOriginalName(attachEntity.getOriginalName());
        attachDTO.setCreatedDate(attachEntity.getCreatedDate());
        attachDTO.setUrl(attachOpenUrl + "/" + attachEntity.getKey());
        return attachDTO;
    }
    private String getExtension (String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }
    private String getYmDString () {
        LocalDate localDate = LocalDate.now();
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        return year + "/" + month + "/" + day;
    }

}
