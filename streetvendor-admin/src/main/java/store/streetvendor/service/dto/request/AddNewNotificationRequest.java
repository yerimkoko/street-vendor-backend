package store.streetvendor.service.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.notification.NotificationType;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class AddNewNotificationRequest {

    @NotNull(message = "제목을 입력해주세요")
    private String title;

    @NotNull(message = "내용을 입력해주세요.")
    private String content;

    private NotificationType notificationType;

    private String notificationImage;

    private LocalDate startDate = LocalDate.now();

    private LocalDate endDate = LocalDate.of(2099, 12, 31);

    @Builder
    public AddNewNotificationRequest(String title, String content, NotificationType notificationType, String notificationImage, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.content = content;
        this.notificationType = notificationType;
        this.notificationImage = notificationImage;
        this.startDate = startDateEq(startDate);
        this.endDate = endDateEq(endDate);
    }

    private LocalDate startDateEq(LocalDate startDate) {
        if (startDate == null) {
            return LocalDate.now();
        }
        return startDate;
    }

    private LocalDate endDateEq(LocalDate endDate) {
        if (endDate == null) {
            return LocalDate.of(2099, 12, 31);
        }
        return endDate;
    }


}
