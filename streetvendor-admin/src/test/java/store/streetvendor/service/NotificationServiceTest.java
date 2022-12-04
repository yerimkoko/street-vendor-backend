package store.streetvendor.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.core.domain.admin.Admin;
import store.streetvendor.core.domain.admin.AdminRepository;
import store.streetvendor.core.domain.notification.Notification;
import store.streetvendor.core.domain.notification.NotificationRepository;
import store.streetvendor.core.domain.notification.NotificationType;
import store.streetvendor.dto.request.AddNewNotificationRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AdminRepository adminRepository;

    @AfterEach
    void cleanUp() {
        notificationRepository.deleteAll();
        adminRepository.deleteAll();
    }

    @Test
    void 관리자가_공지사항을_등록한다() {
        // given
        String title = "제목입니다.";
        String content = "내용입니다.";
        String imageUrl = "123234";
        NotificationType type = NotificationType.EVENT;
        Admin admin = adminRepository.save(Admin.newAdmin("g", "g"));

        AddNewNotificationRequest request = AddNewNotificationRequest
            .builder()
            .notificationType(type)
            .notificationImage(imageUrl)
            .content(content)
            .title(title)
            .build();

        // when
        notificationService.addNotificationService(admin.getId(), request);

        // then
        List<Notification> notifications = notificationRepository.findAll();
        assertThat(notifications).hasSize(1);
        assertThat(notifications.get(0).getNotificationImage()).isEqualTo(imageUrl);
        assertThat(notifications.get(0).getTitle()).isEqualTo(title);
        assertThat(notifications.get(0).getContent()).isEqualTo(content);
        assertThat(notifications.get(0).getNotificationType()).isEqualTo(type);

    }


    @Test
    void 관리자가_공지사항을_삭제한다() {
        // given
        String title = "제목입니다.";
        String content = "내용입니다.";
        String imageUrl = "123234";
        NotificationType type = NotificationType.EVENT;
        Admin admin = adminRepository.save(Admin.newAdmin("g", "g"));
        Notification notification = notificationRepository.save(Notification.newNotification(title, content, type, imageUrl, LocalDate.now(), LocalDate.now().plusDays(1)));

        // when
        notificationService.deleteNotification(admin.getId(), notification.getId());

        // then
        List<Notification> notifications = notificationRepository.findAll();
        assertThat(notifications).isEmpty();


    }



}
