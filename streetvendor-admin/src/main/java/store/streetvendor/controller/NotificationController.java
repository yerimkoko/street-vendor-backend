package store.streetvendor.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.core.config.auth.Auth;
import store.streetvendor.core.config.auth.MemberId;
import store.streetvendor.core.utils.dto.ApiResponse;
import store.streetvendor.dto.request.AddNewNotificationRequest;
import store.streetvendor.dto.request.NotificationListRequest;
import store.streetvendor.dto.response.NotificationResponse;
import store.streetvendor.service.NotificationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Auth
    @ApiOperation("관리자가 공지사항을 등록한다.")
    @PostMapping("/api/v1/notification")
    public ApiResponse<String> addNotification(@RequestBody AddNewNotificationRequest request,
                                               @MemberId Long adminId) {
        notificationService.addNotificationService(adminId, request);
        return ApiResponse.OK;
    }

    @ApiOperation("공지사항을 조회한다")
    @GetMapping("/api/v1/notification")
    public ApiResponse<List<NotificationResponse>> getNotifications(@RequestBody NotificationListRequest request) {
        return ApiResponse.success(notificationService.getNotificationResponse(request));
    }

}
