package store.streetvendor.controller.orderHistory;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.config.auth.Auth;
import store.streetvendor.config.auth.MemberId;
import store.streetvendor.controller.ApiResponse;
import store.streetvendor.service.order_history.OrderHistoryService;
import store.streetvendor.service.order_history.dto.response.MemberOrderHistoryResponse;
import store.streetvendor.service.order_history.dto.response.OrderHistoryResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;

    @Auth
    @ApiOperation(value = "사장님이 orderHistory 를 가져온다")
    @GetMapping("/api/v1/order/histories")
    public ApiResponse<List<OrderHistoryResponse>> getOrderHistoryList(@RequestParam Long storeId, @MemberId Long bossId) {
        return ApiResponse.success(orderHistoryService.getOrderHistory(storeId, bossId));
    }

    @Auth
    @ApiOperation(value = "사용자가 모든 주문을 확인한다")
    @GetMapping("/api/v1/order/my-orders")
    public ApiResponse<List<MemberOrderHistoryResponse>> getOrderList(@MemberId Long memberId) {
        return ApiResponse.success(orderHistoryService.allOrders(memberId));
    }


}
