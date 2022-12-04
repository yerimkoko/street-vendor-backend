package store.streetvendor.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.notification.NotificationType;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class UpdateNotificationRequest {

    private String title;

    private String content;

    private NotificationType type;

    private String notificationImage;

    private LocalDate startDate;

    private LocalDate endDate;

    @Builder
    public UpdateNotificationRequest(String title, String content, NotificationType type, String notificationImage, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.notificationImage = notificationImage;
        this.startDate = startDate;
        this.endDate = endDate;
    }


}
