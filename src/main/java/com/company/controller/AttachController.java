package com.company.controller;

import com.company.dto.attach.AttachDTO;
import com.company.exception.ItemNotFoundException;
import com.company.service.AttachService;
import com.company.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;
    @PostMapping("/upload/{medicineId}")
    public ResponseEntity<?> createAttach(@RequestParam("file") MultipartFile file, @PathVariable("medicineId")
            int medicineId) {
        AttachDTO attachDTO = attachService.saveFile(file, medicineId);
        return ResponseEntity.ok(attachDTO);
    }

    @GetMapping("/load/{fileName}")
    public ResponseEntity<?> loadFile (@PathVariable("fileName") String fileName) {
        Resource resource = attachService.load(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
    @GetMapping(value = "/get/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable("fileName") String fileName) {
        try {
            return attachService.loadAttach(fileName);
        }catch (IOException e){
            e.printStackTrace();
        }
        throw new ItemNotFoundException("Image by this id not found");
    }
    @DeleteMapping("/delete/{key}")
    public void delete (@PathVariable("key") String key) {
        ResponseEntity.ok(attachService.delete(key));
    }
}
