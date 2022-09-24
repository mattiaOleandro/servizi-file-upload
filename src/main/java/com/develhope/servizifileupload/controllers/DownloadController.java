package com.develhope.servizifileupload.controllers;

import org.apache.commons.io.FilenameUtils;
import com.develhope.servizifileupload.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/{fileName}")
    public ResponseEntity downloadFile(@PathVariable String fileName, HttpServletResponse response) throws  Exception{
        try {
            System.out.println("Downloading "  + fileName);
            String extension = FilenameUtils.getExtension(fileName);
            switch (extension){
                case "gif":
                    response.setContentType(MediaType.IMAGE_GIF_VALUE);
                    break;
                case "jpg":
                case "jpeg":
                    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                    break;
                case "png":
                    response.setContentType(MediaType.IMAGE_PNG_VALUE);
                    break;
                case "pdf":
                    response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            }
            response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
            fileStorageService.download(fileName);
            return ResponseEntity.ok("Download " + fileName + " file..." );
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<String>(
                    "There was a problem downloading the file!" +
                            e.getMessage() + "\n" + e.getStackTrace(),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
