package store.streetvendor.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.config.Admin;
import store.streetvendor.config.AdminId;
import store.streetvendor.core.utils.dto.ApiResponse;
import store.streetvendor.service.dto.request.AddNewNotificationRequest;
import store.streetvendor.service.dto.request.NotificationListRequest;
import store.streetvendor.service.dto.request.UpdateNotificationRequest;
import store.streetvendor.service.dto.response.NotificationResponse;
import store.streetvendor.service.NotificationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @Admin
    @ApiOperation("관리자가 공지사항을 등록한다.")
    @PostMapping("/api/v1/notification")
    public ApiResponse<String> addNotification(@RequestBody AddNewNotificationRequest request,
                                               @AdminId Long adminId) {
        notificationService.addNotificationService(adminId, request);
        return ApiResponse.OK;
    }

    @ApiOperation("공지사항을 조회한다")
    @GetMapping("/api/v1/notification")
    public ApiResponse<List<NotificationResponse>> getNotifications(@RequestBody NotificationListRequest request) {
        return ApiResponse.success(notificationService.getNotificationResponse(request));
    }

    @Admin
    @ApiOperation("관리자가 공지사항을 삭제한다")
    @DeleteMapping("/api/v1/notification/{notificationId}")
    public ApiResponse<String> deleteNotification(@AdminId Long adminId, @PathVariable Long notificationId) {
        notificationService.deleteNotification(adminId, notificationId);
        return ApiResponse.OK;
    }

    @Admin
    @ApiOperation("관리자가 공지사항을 수정한다.")
    @PutMapping("/api/v1/notification/{notificationId}")
    public ApiResponse<String> updateNotification(@AdminId Long adminId, @PathVariable Long notificationId, @RequestBody UpdateNotificationRequest request) {
        notificationService.updateNotification(adminId, notificationId, request);
        return ApiResponse.OK;
    }

}
