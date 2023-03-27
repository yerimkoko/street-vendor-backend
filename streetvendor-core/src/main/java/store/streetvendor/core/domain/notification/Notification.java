package store.streetvendor.core.domain.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private String notificationImage;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Builder
    public Notification(String title, String content, String notificationImage, NotificationType notificationType, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.content = content;
        this.notificationImage = notificationImage;
        this.notificationType = notificationType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void update(String title, String content, String notificationImage, NotificationType notificationType, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.content = content;
        this.notificationImage = notificationImage;
        this.notificationType = notificationType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Notification newNotification(String title, String content, NotificationType notificationType, String notificationImage, LocalDate startDate, LocalDate endDate) {
        return Notification.builder()
            .content(content)
            .title(title)
            .notificationType(notificationType)
            .notificationImage(notificationImage)
            .startDate(startDate)
            .endDate(endDate)
            .build();
    }

    public static Notification newFaq(String title, String content, String notificationImage) {
        return Notification.builder()
            .title(title)
            .content(content)
            .notificationType(NotificationType.FAQ_USER)
            .notificationImage(notificationImage)
            .build();
    }
}
