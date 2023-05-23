package store.streetvendor.core.utils.dto.notification.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import store.streetvendor.core.domain.notification.Notification;
import store.streetvendor.core.domain.notification.NotificationType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class FaqRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Nullable
    private String imageUrl;

    @NotNull
    private NotificationType notificationType;


    public FaqRequest(String title, String content, @Nullable String imageUrl, NotificationType type) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.notificationType = type;
    }

    public Notification toEntity() {
        return Notification.newFaq(title, content, imageUrl, notificationType);
    }

}
