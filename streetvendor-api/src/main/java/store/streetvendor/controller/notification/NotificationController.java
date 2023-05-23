package store.streetvendor.controller.notification;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.core.domain.notification.NotificationType;
import store.streetvendor.core.utils.ApiResponse;
import store.streetvendor.core.utils.dto.notification.request.FaqRequest;
import store.streetvendor.core.utils.dto.notification.response.FaqResponse;
import store.streetvendor.domainservice.service.NotificationPlatformService;
import store.streetvendor.domainservice.service.response.NotificationDetailResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationPlatformService notificationService;

    @ApiOperation("[공통, Faq] faq 리스트를 불러온다.")
    @GetMapping("/api/v1/notification/faq/{type}")
    public ApiResponse<List<FaqResponse>> findFaqList(@PathVariable NotificationType type) {
        return ApiResponse.success(notificationService.getFaqList(type));
    }

    @ApiOperation("[ADMIN, 개발용] [Faq] faq 를 등록한다")
    @PostMapping("/api/v1/notification/faq")
    public ApiResponse<String> createFaq(@RequestBody FaqRequest request) {
        notificationService.createFaq(request);
        return ApiResponse.OK;
    }

    @ApiOperation("[공통, FAQ] 공지사항 상세조회")
    @GetMapping("/api/v1/notification/{notificationId}")
    public ApiResponse<NotificationDetailResponse> getNotificationDetail(@PathVariable Long notificationId) {
        return ApiResponse.success(notificationService.getFaqDetail(notificationId));
    }


}
