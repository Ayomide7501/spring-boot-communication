package net.elevatedlifestyle.communication.service.Impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import lombok.extern.slf4j.Slf4j;
import net.elevatedlifestyle.communication.service.AwsS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;



@Service
@Slf4j
public class AwsS3ServiceImpl implements AwsS3Service {

    @Autowired
    private AmazonS3 amazonS3;
    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Override
    public String upload(String path, MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        amazonS3.putObject(bucketName, path + fileName, inputStream, metadata);
        //return amazonS3.getUrl(bucketName,path + fileName).toString();
        return "https://"+bucketName + ".s3.us-east-2.amazonaws.com/"+path+fileName;
    }
    @Override
    public S3Object download(String path, String fileName) {
        return amazonS3.getObject(path, fileName);
    }
}
