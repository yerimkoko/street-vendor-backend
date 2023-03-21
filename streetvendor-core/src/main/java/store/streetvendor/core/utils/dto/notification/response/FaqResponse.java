package store.streetvendor.core.utils.dto.notification.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.notification.Notification;

@NoArgsConstructor
@Getter
public class FaqResponse {

    private String title;

    private String content;

    private String image;

    @Builder
    public FaqResponse(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public static FaqResponse of(Notification notification) {
        return FaqResponse.builder()
            .title(notification.getTitle())
            .content(notification.getContent())
            .image(notification.getNotificationImage())
            .build();
    }
}
