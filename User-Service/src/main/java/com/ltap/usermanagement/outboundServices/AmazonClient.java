package com.ltap.usermanagement.outboundServices;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Service
public class AmazonClient {

  private AmazonS3 s3client;

  @Value("${amazonProperties.endpointUrl}")
  private String endpointUrl;

  @Value("${amazonProperties.bucketName}")
  private String bucketName;

  @Value("${amazonProperties.accessKey}")
  private String accessKey;

  @Value("${amazonProperties.secretKey}")
  private String secretKey;

  @Value("${amazonProperties.region}")
  private String region;

  @PostConstruct
  private void initializeAmazon() {

    Regions selectRegion;

    selectRegion = Regions.fromName(region);

    BasicAWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);
    this.s3client =
        AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(creds))
            .withRegion(selectRegion)
            .build();
  }

  public String uploadFile(MultipartFile multipartFile) {
    String fileUrl = "";
    try {
      File file = convertMultiPartToFile(multipartFile);
      String fileName = generateFileName(multipartFile);
      fileUrl = "https://" + bucketName + "." + endpointUrl + "/" + fileName;
      uploadFileTos3bucket(fileName, file);
      file.delete();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fileUrl;
  }

  private File convertMultiPartToFile(MultipartFile file) throws IOException {
    File convFile = new File(file.getOriginalFilename());
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();
    return convFile;
  }

  private String generateFileName(MultipartFile multiPart) {
    return new Date().getTime()
        + "-"
        + multiPart.getOriginalFilename().replaceAll("[^.a-zA-Z0-9]", "_");
  }

  public URL downloadS3Object(String fileUrl) {
    // String fileUrl = "";
    URL fileArn = null;
    try {

      // fileUrl = "https://" + bucketName + "." + endpointUrl + "/" + foleUrl;
      fileArn = downloadFileFromS3bucket(fileUrl, bucketName);
      //  return s3Object;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fileArn;
  }

  private void uploadFileTos3bucket(String fileName, File file) {

    // s3client.putObject(new PutObjectRequest(bucketName, fileName, file));

    s3client.putObject(
        new PutObjectRequest(bucketName, fileName, file)
            .withCannedAcl(CannedAccessControlList.PublicRead));
  }

  private URL downloadFileFromS3bucket(String fileName, String bucketName) {
    URL s3ObjectUrl = s3client.getUrl(bucketName, fileName);
    return s3ObjectUrl;
  }

  public String deleteFileFromS3Bucket(String fileUrl) {
    String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    return "Successfully deleted";
  }

  public AWSCognitoIdentityProvider getAmazonCognitoIdentityClient() {
    Regions selectRegion;
    selectRegion = Regions.fromName(region);
    BasicAWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);

    selectRegion = Regions.fromName(region);
    return AWSCognitoIdentityProviderClientBuilder.standard()
        //            .withCredentials(new
        // AWSStaticCredentialsProvider(creds)).withRegion(selectRegion).build();
        .withCredentials(new DefaultAWSCredentialsProviderChain())
        .withRegion(selectRegion)
        .build();
  }
}
