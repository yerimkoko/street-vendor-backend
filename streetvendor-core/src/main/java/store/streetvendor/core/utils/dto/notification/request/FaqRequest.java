package store.streetvendor.core.utils.dto.notification.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.notification.Notification;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class FaqRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String imageUrl;

    public FaqRequest(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public Notification toEntity() {
        return Notification.newFaq(title, content, imageUrl);
    }

}
