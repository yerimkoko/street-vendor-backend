package store.streetvendor.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.admin.Admin;
import store.streetvendor.core.domain.admin.AdminRepository;
import store.streetvendor.core.domain.notification.Notification;
import store.streetvendor.core.domain.notification.NotificationRepository;
import store.streetvendor.core.exception.NotFoundException;
import store.streetvendor.dto.request.AddNewNotificationRequest;
import store.streetvendor.dto.request.NotificationListRequest;
import store.streetvendor.dto.request.UpdateNotificationRequest;
import store.streetvendor.dto.response.NotificationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
public class NotificationService {

    private final AdminRepository adminRepository;

    private final NotificationRepository notificationRepository;

    @Transactional
    public void addNotificationService(Long adminId, AddNewNotificationRequest request) {
        Admin admin = adminRepository.findByAdminId(adminId);
        if (admin == null) {
            throw new NotFoundException(String.format("[%s]에 해당하는 관리자가 존재하지 않습니다. 다시 확인해주세요", adminId));
        }
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
        Admin admin = adminRepository.findByAdminId(adminId);
        if (admin == null) {
            throw new NotFoundException(String.format("[%s]에 해당하는 관리자는 존재하지 않습니다.", adminId));
        }

        Notification notification = notificationRepository.findByNotificationId(notificationId);
        if (notification == null) {
            throw new NotFoundException(String.format("[%s]에 해당하는 공지사항은 존재하지 않습니다.", notificationId));
        }

        notificationRepository.delete(notification);

    }

}
