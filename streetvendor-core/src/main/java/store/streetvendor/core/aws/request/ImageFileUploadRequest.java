package store.streetvendor.core.aws.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import store.streetvendor.core.aws.ImageFileType;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageFileUploadRequest implements FileUploadRequest{

    @NotNull
    private MultipartFile file;

    @NotNull
    private ImageFileType type;

    public ImageFileUploadRequest(MultipartFile file, ImageFileType type) {
        this.file = file;
        this.type = type;
    }

    public static ImageFileUploadRequest of(MultipartFile file, ImageFileType type) {
        return new ImageFileUploadRequest(file, type);
    }


    @Override
    public String getFileNameWithBucketDirectory(@Nullable String originalFileName) {
        return type.createUniqueFileNameWithExtension(originalFileName);
    }
}
