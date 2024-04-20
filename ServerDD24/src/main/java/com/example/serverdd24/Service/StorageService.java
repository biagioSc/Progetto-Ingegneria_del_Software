package com.example.serverdd24.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.example.serverdd24.Controller.UtenteController;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.regions.Regions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import java.net.URL;

@Service
public class StorageService {

    private final AmazonS3 amazonS3;
    private static final Logger logger = LoggerFactory.getLogger(UtenteController.class);

    @Value("${app.s3.bucket-name}")
    private String bucketName;

    @Autowired
    public StorageService(@Value("${aws.access-key-id}") String awsId,
                          @Value("${aws.secret-access-key}") String awsKey,
                          @Value("${aws.region}") String region) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsId, awsKey);
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }

    public String uploadBase64Image(String keyName, String base64Image, String contentType) {
        // Decodifica la stringa Base64 in un array di byte
        try {
            byte[] imageBytes = Base64.decodeBase64(base64Image);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(imageBytes.length);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
            amazonS3.putObject(bucketName, keyName, byteArrayInputStream, metadata);
            String url = amazonS3.getUrl(bucketName, keyName).toString();

            return url;
        } catch (SdkClientException e) {
            throw new RuntimeException("Errore durante l'upload del file su S3", e);
        }
    }
    public S3ObjectInputStream downloadFile(String keyName) {
        S3Object s3object = amazonS3.getObject(bucketName, keyName);
        return s3object.getObjectContent();
    }

    public void deleteFile(String fileName) {
        try {
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, fileName);
            amazonS3.deleteObject(deleteObjectRequest);
            logger.info("File {} eliminato con successo.", fileName);
        } catch (AmazonServiceException e) {
            logger.error("AmazonServiceException: Impossibile eliminare il file {}. Messaggio di errore: {}", fileName, e.getMessage());
        } catch (AmazonClientException e) {
            logger.error("AmazonClientException: Impossibile comunicare con S3 per eliminare il file {}. Messaggio di errore: {}", fileName, e.getMessage());
        } catch (Exception e) {
            logger.error("Errore generico durante l'eliminazione del file {}: {}", fileName, e.getMessage());
        }
    }
}

