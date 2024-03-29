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
import store.streetvendor.service.dto.request.AddNewNotificationRequest;
import store.streetvendor.service.dto.request.UpdateNotificationRequest;

import java.time.LocalDate;
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
        Admin admin = Admin.newAdmin("gochi97@naver.com", "yerimkoko");
        adminRepository.save(admin);
        String title = "제목입니다1.";
        String content = "내용입니다2.";
        String imageUrl = "1232343";
        NotificationType type = NotificationType.EVENT;

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
        assertNotification(notifications.get(0), title, content, imageUrl, type);

    }

    private void assertNotification(Notification notification, String title, String content, String imageUrl, NotificationType type) {
        assertThat(notification.getNotificationType()).isEqualTo(type);
        assertThat(notification.getTitle()).isEqualTo(title);
        assertThat(notification.getContent()).isEqualTo(content);
        assertThat(notification.getNotificationImage()).isEqualTo(imageUrl);
    }


    @Test
    void 관리자가_공지사항을_삭제한다() {
        // given
        Admin admin = saveAdmin();
        Notification notification = notificationRepository.save(newNotification());

        // when
        notificationService.deleteNotification(admin.getId(), notification.getId());

        // then
        List<Notification> notifications = notificationRepository.findAll();
        assertThat(notifications).isEmpty();
    }

    @Test
    void 관리자가_공지사항을_수정한다() {
        // given
        Admin admin = saveAdmin();
        String title = "제목입니다1.";
        String content = "내용입니다2.";
        String imageUrl = "1232343";
        NotificationType type = NotificationType.EVENT;
        Notification notification = notificationRepository.save(newNotification());
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(5);

        UpdateNotificationRequest request = UpdateNotificationRequest.builder()
            .content(content)
            .title(title)
            .notificationImage(imageUrl)
            .type(type)
            .startDate(startDate)
            .endDate(endDate)
            .build();

        // when
        notificationService.updateNotification(admin.getId(), notification.getId(), request);

        // then
        List<Notification> notifications = notificationRepository.findAll();
        assertThat(notifications).hasSize(1);
        assertNotification(notifications.get(0), title, content, imageUrl, type);

    }

    private Admin saveAdmin() {
        Admin newAdmin = Admin.newAdmin("gochi97@naver.com", "yerimkoko");
        return adminRepository.save(newAdmin);
    }

    private Notification newNotification() {
        String title = "제목입니다.";
        String content = "내용입니다.";
        String imageUrl = "123234";
        NotificationType type = NotificationType.EVENT;
        return Notification.newNotification(title, content, type, imageUrl, LocalDate.now(), LocalDate.now().plusDays(1));
    }



}
