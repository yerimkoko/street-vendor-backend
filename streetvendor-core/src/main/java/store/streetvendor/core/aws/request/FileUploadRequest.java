package store.streetvendor.core.aws.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public interface FileUploadRequest {

    @NotNull
    MultipartFile getFile();

    @NotNull
    FileType getType();


    default String getFileNameWithBucketDirectory(String originalFileName) {
        return getType().createUniqueFileNameWithExtension(originalFileName);
    }

}
