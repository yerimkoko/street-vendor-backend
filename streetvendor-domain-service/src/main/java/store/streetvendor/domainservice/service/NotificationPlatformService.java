package store.streetvendor.domainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.notification.Notification;
import store.streetvendor.core.domain.notification.NotificationRepository;
import store.streetvendor.core.domain.notification.NotificationType;
import store.streetvendor.core.exception.NotFoundException;
import store.streetvendor.core.utils.dto.notification.request.FaqRequest;
import store.streetvendor.core.utils.dto.notification.response.FaqResponse;
import store.streetvendor.domainservice.service.response.NotificationDetailResponse;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationPlatformService {

    private final NotificationRepository notificationRepository;

    @Transactional(readOnly = true)
    public List<FaqResponse> getFaqList(@NotNull NotificationType type) {
        return notificationRepository.findNotificationByNotificationType(type).stream()
            .map(FaqResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public void createFaq(FaqRequest request) {
        notificationRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public NotificationDetailResponse getFaqDetail(Long notificationId) {
        Notification notification = notificationRepository.findByNotificationId(notificationId);
        if (notification == null) {
            throw new NotFoundException(String.format("[%s]에 해당하는 공지사항은 존재하지 않습니다.", notificationId));
        }
        return NotificationDetailResponse.of(notification);
    }

}
