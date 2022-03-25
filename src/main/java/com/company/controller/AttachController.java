package com.company.controller;

import com.company.dto.attach.AttachDTO;
import com.company.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file) {
        AttachDTO attachDTO = attachService.saveFile(file);
        return ResponseEntity.ok(attachDTO);
    }
}
