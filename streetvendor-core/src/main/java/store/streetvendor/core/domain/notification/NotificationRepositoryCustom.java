package store.streetvendor.core.domain.notification;

import java.time.LocalDate;
import java.util.List;

public interface NotificationRepositoryCustom {

    Notification findByNotificationId(Long id);

    List<Notification> findNotificationByStartTimeAndEndTime(LocalDate startDate, LocalDate endDate);

    List<Notification> findNotificationByNotificationType(NotificationType type);
}
