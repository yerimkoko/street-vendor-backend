package store.streetvendor.service.notification.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.notification.Notification;

@NoArgsConstructor
@Getter
public class NotificationDetailResponse {

    private Long notificationId;

    private String imageUrl;

    private String title;

    private String content;

    private String type;

    @Builder
    public NotificationDetailResponse(Long notificationId, String imageUrl, String title, String content, String type) {
        this.notificationId = notificationId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.content = content;
        this.type = type;
    }

    public static NotificationDetailResponse of(Notification notification) {
        return NotificationDetailResponse.builder()
            .notificationId(notification.getId())
            .imageUrl(notification.getNotificationImage())
            .title(notification.getTitle())
            .content(notification.getContent())
            .type(notification.getNotificationType().description)
            .build();
    }
}
