package com.movielix.movieApi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {


    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        //pegando o nome do arquivo

        String fileName = file.getOriginalFilename();

        // pegando o arquivo

        String filePath = path + File.separator + fileName;

        // Arquivo Objeto

        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        //copiando o arquivo ou upload file para o path

        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;
        return new FileInputStream(filePath);
    }
}
