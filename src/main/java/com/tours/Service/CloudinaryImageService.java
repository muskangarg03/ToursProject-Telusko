package com.tours.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class CloudinaryImageService {

    private static final Logger logger = Logger.getLogger(ImageService.class.getName());

    private final Cloudinary cloudinary;

    // Constructor to initialize Cloudinary with credentials from application.properties
    public CloudinaryImageService(
            @Value("${cloudinary.cloud-name}") String cloudName,
            @Value("${cloudinary.api-key}") String apiKey,
            @Value("${cloudinary.api-secret}") String apiSecret
    ) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    // Method to upload an image to Cloudinary
    public String uploadImage(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            // Upload file to Cloudinary
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.emptyMap());

            // Return the secure URL of the uploaded image
            String imageUrl = (String) uploadResult.get("secure_url");

            logger.info("Image uploaded successfully to Cloudinary: " + imageUrl);
            return imageUrl;

        } catch (IOException e) {
            logger.severe("Failed to upload image to Cloudinary: " + e.getMessage());
            throw new RuntimeException("Failed to upload image: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warning(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    // Method to update an existing image in Cloudinary
    public String updateImage(String oldImageUrl, MultipartFile newFile) {
        try {
            if (newFile == null || newFile.isEmpty()) {
                throw new IllegalArgumentException("New file is empty or null");
            }

            // If an old image URL exists, try to delete it from Cloudinary
            if (oldImageUrl != null && !oldImageUrl.trim().isEmpty()) {
                try {
                    // Extract public ID from the Cloudinary URL
                    String publicId = extractPublicIdFromUrl(oldImageUrl);

                    // Delete the old image from Cloudinary
                    if (publicId != null) {
                        Map<?, ?> deleteResult = cloudinary.uploader().destroy(publicId,
                                ObjectUtils.emptyMap());
                        logger.info("Old image deleted from Cloudinary: " + deleteResult);
                    }
                } catch (Exception e) {
                    logger.warning("Could not delete old image from Cloudinary: " + e.getMessage());
                }
            }

            // Upload the new file to Cloudinary
            return uploadImage(newFile);

        } catch (RuntimeException e) {
            logger.severe("Failed to update image in Cloudinary: " + e.getMessage());
            throw new RuntimeException("Failed to update image: " + e.getMessage());
        }
    }

    // Method to delete an image from Cloudinary
    public void deleteImage(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            logger.warning("Attempted to delete null or empty image URL");
            return;
        }

        try {
            // Extract public ID from the Cloudinary URL
            String publicId = extractPublicIdFromUrl(imageUrl);

            if (publicId == null) {
                logger.warning("Could not extract public ID from URL: " + imageUrl);
                return;
            }

            // Delete the image from Cloudinary
            Map<?, ?> deleteResult = cloudinary.uploader().destroy(publicId,
                    ObjectUtils.emptyMap());

            logger.info("Image deleted successfully from Cloudinary: " + deleteResult);

        } catch (IOException e) {
            logger.severe("Failed to delete image from Cloudinary: " + e.getMessage());
            throw new RuntimeException("Failed to delete image: " + e.getMessage());
        }
    }

    // Helper method to extract public ID from Cloudinary URL
    private String extractPublicIdFromUrl(String url) {
        if (url == null) return null;

        // Cloudinary URLs typically end with /upload/v{version}/{publicId}
        String[] urlParts = url.split("/");
        for (int i = 0; i < urlParts.length; i++) {
            if (urlParts[i].equals("upload") && i + 2 < urlParts.length) {
                // Get the public ID, removing file extension
                String publicId = urlParts[i + 2];
                int dotIndex = publicId.lastIndexOf('.');
                return dotIndex > 0 ? publicId.substring(0, dotIndex) : publicId;
            }
        }
        return null;
    }
}