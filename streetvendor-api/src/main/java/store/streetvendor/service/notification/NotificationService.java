package store.streetvendor.service.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.notification.NotificationRepository;
import store.streetvendor.core.domain.notification.NotificationType;
import store.streetvendor.core.utils.dto.notification.request.FaqRequest;
import store.streetvendor.core.utils.dto.notification.response.FaqResponse;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional(readOnly = true)
    public List<FaqResponse> getFaqList(NotificationType type) {
        return notificationRepository.findNotificationByNotificationType(type).stream()
            .map(FaqResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public void createFaq(FaqRequest request) {
        notificationRepository.save(request.toEntity());
    }

}
