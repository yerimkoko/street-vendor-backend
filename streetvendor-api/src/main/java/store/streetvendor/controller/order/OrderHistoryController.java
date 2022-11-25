package store.streetvendor.controller.order;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.core.config.auth.Auth;
import store.streetvendor.core.config.auth.MemberId;
import store.streetvendor.core.utils.dto.ApiResponse;
import store.streetvendor.core.utils.dto.order_history.response.OrderDetailResponse;
import store.streetvendor.core.utils.dto.response.MemberOrderHistoryResponse;
import store.streetvendor.service.order.OrderHistoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;

    @Auth
    @ApiOperation(value = "[사용자] 사용자가 모든 주문을 확인한다")
    @GetMapping("/api/v1/order/my-orders")
    public ApiResponse<List<MemberOrderHistoryResponse>> getOrderList(@MemberId Long memberId) {
        return ApiResponse.success(orderHistoryService.allOrders(memberId));
    }

    @Auth
    @ApiOperation(value = "[사용자] 주문 내역을 상세 조회한다")
    @GetMapping("/api/v1/order/{orderId}")
    public ApiResponse<OrderDetailResponse> getOrderDetail(@MemberId Long memberId, @PathVariable Long orderId) {
        return ApiResponse.success(orderHistoryService.getOrderDetail(memberId, orderId));
    }




}
