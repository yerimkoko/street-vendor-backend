package store.streetvendor.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.notification.Notification;
import store.streetvendor.core.domain.notification.NotificationType;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class NotificationResponse {

    private Long notificationId;

    private NotificationType type;

    private String title;

    private String content;

    private String imageUrl;

    private LocalDate startTime;

    @Builder
    public NotificationResponse(Long notificationId, NotificationType type, String title, String content, LocalDate startTime, String imageUrl) {
        this.notificationId = notificationId;
        this.type = type;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.startTime = startTime;
    }

    public static NotificationResponse of(Notification notification) {
        return NotificationResponse.builder()
            .imageUrl(notification.getNotificationImage())
            .title(notification.getTitle())
            .content(notification.getContent())
            .type(notification.getNotificationType())
            .notificationId(notification.getId())
            .startTime(notification.getStartDate())
            .build();
    }
}
