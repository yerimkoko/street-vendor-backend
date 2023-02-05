package store.streetvendor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.admin.AdminRepository;
import store.streetvendor.core.domain.notification.Notification;
import store.streetvendor.core.domain.notification.NotificationRepository;
import store.streetvendor.core.exception.NotFoundException;
import store.streetvendor.service.dto.request.AddNewNotificationRequest;
import store.streetvendor.service.dto.request.NotificationListRequest;
import store.streetvendor.service.dto.request.UpdateNotificationRequest;
import store.streetvendor.service.dto.response.NotificationResponse;
import store.streetvendor.service.utils.AdminServiceUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final AdminRepository adminRepository;

    private final NotificationRepository notificationRepository;

    @Transactional
    public void addNotificationService(Long adminId, AddNewNotificationRequest request) {
        AdminServiceUtils.findAdmin(adminId, adminRepository);
        notificationRepository.save(Notification.newNotification(request.getTitle(), request.getContent(), request.getNotificationType(), request.getNotificationImage(), request.getStartDate(), request.getEndDate()));
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationResponse(NotificationListRequest request) {
        return notificationRepository.findNotificationByStartTimeAndEndTime(request.getStartDate(), request.getEndDate())
            .stream()
            .map(NotificationResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteNotification(Long adminId, Long notificationId) {
        AdminServiceUtils.findAdmin(adminId, adminRepository);
        Notification notification = notificationRepository.findByNotificationId(notificationId);
        if (notification == null) {
            throw new NotFoundException(String.format("[%s]에 해당하는 공지사항은 존재하지 않습니다.", notificationId));
        }

        notificationRepository.delete(notification);

    }

    @Transactional
    public void updateNotification(Long adminId, Long notificationId, UpdateNotificationRequest request) {
        AdminServiceUtils.findAdmin(adminId, adminRepository);
        Notification notification = notificationRepository.findByNotificationId(notificationId);
        if (notification == null) {
            throw new NotFoundException(String.format("[%s]에 해당하는 공지사항은 존재하지 않습니다.", notificationId));
        }
        notification.update(request.getTitle(), request.getContent(), request.getNotificationImage(), request.getType(), request.getStartDate(), request.getEndDate());
    }

}
