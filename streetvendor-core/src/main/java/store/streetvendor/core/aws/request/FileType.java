package store.streetvendor.core.aws.request;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public interface FileType {

    @NotNull
    String createUniqueFileNameWithExtension(@Nullable String originalFileName);


}
