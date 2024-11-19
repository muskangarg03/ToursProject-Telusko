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

    // Method to upload an image to the give directory and set its unique name
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


    //Method to update an existing image with the new one
    public String updateImage(String oldFilename, MultipartFile newFile) {
        try {
            if (newFile == null || newFile.isEmpty()) {
                throw new IllegalArgumentException("New file is empty or null");
            }

            // Try to delete the old file if it exists
            if (oldFilename != null && !oldFilename.trim().isEmpty()) {
                Path oldFilePath = Paths.get(UPLOAD_DIR, oldFilename);
                File oldFile = oldFilePath.toFile();

                if (oldFile.exists()) {
                    if (!oldFile.delete()) {
                        logger.warning("Could not delete old file: " + oldFilename);
                    } else {
                        logger.info("Successfully deleted old file: " + oldFilename);
                    }
                }
            }

            // Upload the new file regardless of whether the old file was deleted
            String newFilename = uploadImage(newFile);
            logger.info("Successfully updated image. New filename: " + newFilename);
            return newFilename;

        } catch (RuntimeException e) {
            logger.severe("Failed to update image: " + e.getMessage());
            throw new RuntimeException("Failed to update image: " + e.getMessage());
        }
    }


    //Method to delete the existing images
    public void deleteImage(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            logger.warning("Attempted to delete null or empty filename");
            return;
        }

        try {
            Path filePath = Paths.get(UPLOAD_DIR, filename);
            File file = filePath.toFile();

            if (!file.exists()) {
                logger.warning("File does not exist: " + filename);
                return;
            }

            if (!file.delete()) {
                throw new IOException("Failed to delete file: " + filename);
            }

            logger.info("File deleted successfully: " + filename);

        } catch (IOException e) {
            logger.severe("Failed to delete image: " + e.getMessage());
            throw new RuntimeException("Failed to delete image: " + e.getMessage());
        }
    }

}