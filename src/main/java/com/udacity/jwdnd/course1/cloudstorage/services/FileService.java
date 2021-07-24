package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public void addFile(MultipartFile mpFile, Integer userId) throws IOException {
        File file = new File(
                0,
                mpFile.getOriginalFilename(),
                mpFile.getContentType(),
                String.valueOf(mpFile.getSize()),
                userId,
                mpFile.getBytes()
        );
        fileMapper.addFile(file);
    }

    public List<File> getUserFiles(Integer userId) {
        return fileMapper.getFilesByUser(userId);
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFileById(fileId);
    }

    public File getFileByName(String fileName) {
        return fileMapper.getFileByName(fileName);
    }

    public void deleteFile(Integer fileId) {
        fileMapper.deleteFile(fileId);
    }
}
