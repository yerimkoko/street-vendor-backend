package store.streetvendor.core.aws;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import store.streetvendor.core.exception.InvalidException;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileExtensionExtractor {

    @NotNull
    public static String extractExtension(@NotNull String fileName) {
        try {
            String extension = fileName.substring(fileName.lastIndexOf("."));
            if (extension.length() < 2) {
                throw new InvalidException(String.format("잘못된 확장자 형식의 파일 [%s] 입니다.", fileName));
            }
            return extension;
        } catch (StringIndexOutOfBoundsException e) {
            throw new InvalidException(String.format("잘못된 확장자 형식의 파일 [%s] 입니다.", fileName));
        }
    }
}
