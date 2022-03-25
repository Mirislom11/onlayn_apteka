package com.company.service;

import com.company.dto.attach.AttachDTO;
import com.company.entity.AttachEntity;
import com.company.exception.BadRequestException;
import com.company.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class AttachService {
    @Autowired
    private AttachRepository attachRepository;
    @Value("${attach.upload.folder}")
    private String uploadFolder;
    @Value("${attach.open.url}")
    private String attachOpenUrl;
    public AttachDTO saveFile(MultipartFile file) {
        /*File folder = new File("uploads");*/
        String filePath = getYmDString();
        String fileName = UUID.randomUUID().toString();
        String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        File folder = new File( uploadFolder + "/" +filePath );

        if (!folder.exists()) {
            folder.mkdir();
        }
        AttachEntity attachEntity = createAttachEntity(fileName, file.getOriginalFilename(), filePath, extension, file.getSize());
        attachRepository.save(attachEntity);
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setKey(attachEntity.getKey());
        attachDTO.setExtension(attachEntity.getExtension());
        attachDTO.setPath(attachEntity.getPath());
        attachDTO.setSize(attachEntity.getSize());
        attachDTO.setOriginalName(attachEntity.getOriginalName());
        attachDTO.setCreatedDate(attachEntity.getCreatedDate());
        attachDTO.setUrl(attachOpenUrl + "/" + fileName);
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
        }catch (Exception e){
            return new byte[0];
        }
        String extension = getExtension(attachEntity.getExtension());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalName, extension, baos);
        imageByte = baos.toByteArray();
        baos.flush();
        baos.close();
        return imageByte;
    }
    public void delete (String key) {
        Optional<AttachEntity> optional = attachRepository.findByKey(key);
        if (!optional.isPresent()) {
            return;
        }
        String filePath = optional.get().getPath() + "/" +key+ "." + optional.get().getExtension();
        File file = new File(filePath);
        file.delete();
        attachRepository.delete(optional.get());
    }

    public AttachEntity createAttachEntity(String fileName, String originalName,String path, String extension, long size){
        AttachEntity attachEntity = new AttachEntity();
        attachEntity.setPath(path);
        attachEntity.setExtension(extension);
        attachEntity.setKey(fileName);
        attachEntity.setOriginalName(originalName);
        attachEntity.setSize(size);
        return attachEntity;
    }

    public String getExtension (String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }
    public String getYmDString () {
        LocalDate localDate = LocalDate.now();
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        return year + "/" + month + "/" + day;
    }

}
