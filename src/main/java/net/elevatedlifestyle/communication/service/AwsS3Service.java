package net.elevatedlifestyle.communication.service;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface AwsS3Service {
    public String upload(String path, MultipartFile file) throws IOException;

    public S3Object download(String path, String fileName);
}
