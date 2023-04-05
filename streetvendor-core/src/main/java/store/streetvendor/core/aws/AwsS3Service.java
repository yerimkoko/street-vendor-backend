package store.streetvendor.core.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import store.streetvendor.core.aws.request.FileUploadRequest;
import store.streetvendor.core.aws.response.ImageUrlResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AwsS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;


    public String upload(MultipartFile multipartFile) {

        String fileName = createFileName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new IllegalArgumentException("이미지 업로드에 실패했습니다.");
        }

        return fileName;

    }

    public ImageUrlResponse uploadImageFile(FileUploadRequest request) {

        String fileName = getFileOriginalName(request);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(request.getFile().getSize());
        objectMetadata.setContentType(request.getFile().getContentType());
        try (InputStream inputStream = request.getFile().getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new IllegalArgumentException("이미지 업로드에 실패했습니다.");
        }

        return ImageUrlResponse.of(fileName);

    }


    public List<ImageUrlResponse> uploadImageFiles(List<FileUploadRequest> requests) {
        List<ImageUrlResponse> imageUrlResponses = new ArrayList<>();
        for (FileUploadRequest request : requests) {
            imageUrlResponses.add(uploadImageFile(request));
        }
        return imageUrlResponses;
    }

    private String getFileOriginalName(FileUploadRequest request) {
        return request.getFileNameWithBucketDirectory(request.getFile().getOriginalFilename());
    }


    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일 [" + fileName + "] 입니다.");
        }
    }


}
