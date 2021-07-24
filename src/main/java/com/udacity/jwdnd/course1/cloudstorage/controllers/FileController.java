package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {

    private final UserService userService;
    private final FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartFile.html
    @PostMapping()
    public String addFile(Authentication authentication, MultipartFile file) throws IOException {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserid();
        String fileName = file.getOriginalFilename();
        if (fileService.getFileByName(fileName) != null) {
            return "redirect:/home?duplicate";
        }
        fileService.addFile(file, userId);
        return "redirect:/result?success";
    }

    @GetMapping(
            value = "/download/{fileId}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
//    https://stackoverflow.com/a/29828342
    @ResponseBody
    public byte[] downloadFile(Authentication authentication, @PathVariable Integer fileId) throws Exception {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserid();
        File file = fileService.getFile(fileId);
        if (file.getUserId().equals(userId)) {
            return file.getFileData();
        } else {
            throw new Exception();
        }
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(Authentication authentication, @PathVariable Integer fileId) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserid();
        File file = fileService.getFile(fileId);
        if (file != null) {
            if (file.getUserId().equals(userId)) {
                fileService.deleteFile(fileId);
            } else {
                return "redirect:/result?error";
            }
        } else {
            return "redirect:/result?error";
        }
        return "redirect:/result?success";
    }
}
