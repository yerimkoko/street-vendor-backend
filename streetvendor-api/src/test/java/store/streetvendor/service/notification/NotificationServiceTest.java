package store.streetvendor.service.notification;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.core.domain.notification.Notification;
import store.streetvendor.core.domain.notification.NotificationRepository;
import store.streetvendor.core.utils.dto.notification.request.FaqRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    @AfterEach
    void cleanUp() {
        notificationRepository.deleteAll();
    }

    @Test
    void 자주하는_질문을_생성한다() {
        // given
        String title = "가게 위치 등록 설명";
        String content = "- 사장님께서 처음 가게 위치를 등록하실때 지도로 좌표를 찍어 등록하게 됩니다.";
        FaqRequest request = new FaqRequest(title, content, null);

        // when
        notificationService.createFaq(request);

        // then
        List<Notification> notifications = notificationRepository.findAll();
        assertThat(notifications).hasSize(1);
        assertThat(notifications.get(0).getTitle()).isEqualTo(title);
        assertThat(notifications.get(0).getContent()).isEqualTo(content);

    }
}
