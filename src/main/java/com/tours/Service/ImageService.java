package com.tours.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;


@Service
public class ImageService {

    private static final Logger logger = Logger.getLogger(ImageService.class.getName());
    private static final String UPLOAD_DIR = "src/main/resources/static/upload_images";

    // Improved method with additional exception handling and logging
    public String uploadImage(MultipartFile file) {
        try {

            if (file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            // Create directory if it doesn't exist
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                boolean dirCreated = directory.mkdirs();
                if (!dirCreated) {
                    throw new IOException("Failed to create directories");
                }
            }

            // Generate unique filename
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, filename);

            // Save the file to the disk
            Files.write(filePath, file.getBytes());

            logger.info("File uploaded successfully: " + filename);

            // Return the filename or full URL depending on your need
            return filename;
        } catch (IOException e) {
            logger.severe("Failed to upload image: " + e.getMessage());
            throw new RuntimeException("Failed to upload image: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Handle case where file is empty
            logger.warning(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
