package store.streetvendor.core.aws;

import lombok.Getter;
import store.streetvendor.core.aws.request.FileType;
import store.streetvendor.core.exception.BadRequestException;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public enum ImageFileType implements FileType {
    REVIEW_IMAGE("review/"),
    STORE_IMAGE("store/"),
    QUESTION_IMAGE("question/"),
    MEMBER_IMAGE("member/")
    ;

    @Nullable
    private final String directory;

    ImageFileType(@Nullable String directory) {
        this.directory = directory;
    }

    @NotNull
    @Override
    public String createUniqueFileNameWithExtension(@Nullable String originalFileName) {
        if (originalFileName == null || originalFileName.isBlank()) {
            throw new BadRequestException("파일의 이름이 없습니다");
        }
        String extension = FileExtensionExtractor.extractExtension(originalFileName);
        return getFileNameWithDirectory(UUID.randomUUID() + extension);
    }

    @NotNull
    private String getFileNameWithDirectory(@NotNull String fileName) {
        return this.directory + fileName;
    }


}
