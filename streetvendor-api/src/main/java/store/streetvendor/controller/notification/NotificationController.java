package store.streetvendor.controller.notification;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.core.utils.ApiResponse;
import store.streetvendor.core.utils.dto.notification.request.FaqRequest;
import store.streetvendor.core.utils.dto.notification.response.FaqResponse;
import store.streetvendor.service.notification.NotificationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @ApiOperation("[Faq] faq 리스트를 불러온다.")
    @GetMapping("/api/v1/notification/faq")
    public ApiResponse<List<FaqResponse>> findFaqList() {
        return ApiResponse.success(notificationService.getFaqList());
    }

    @ApiOperation("[ADMIN, 개발용] [Faq] faq 를 등록한다")
    @PostMapping("/api/v1/notification/faq")
    public ApiResponse<String> createFaq(@RequestBody FaqRequest request) {
        notificationService.createFaq(request);
        return ApiResponse.OK;
    }
}
