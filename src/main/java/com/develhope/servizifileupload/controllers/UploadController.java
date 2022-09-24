package com.develhope.servizifileupload.controllers;

import com.develhope.servizifileupload.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity uploadFile(@RequestParam MultipartFile[] files) throws  Exception{
        try {
            List<String> fileNames = new ArrayList();
            for (MultipartFile file : files) {
                String singleUploadedFileName = fileStorageService.upload(file);
                fileNames.add(singleUploadedFileName);
            }
            System.out.println("File " + fileNames +" upload successful");
            return ResponseEntity.ok("File " + fileNames +" upload successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<String>(
                    "There was a problem uploading files!\n" +
                            e.getMessage() + "\n" + e.getStackTrace(),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
