package store.streetvendor.controller.order;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.core.config.auth.Auth;
import store.streetvendor.core.config.auth.MemberId;
import store.streetvendor.core.service.utils.dto.ApiResponse;
import store.streetvendor.service.order.OrderService;
import store.streetvendor.core.service.utils.dto.AddNewOrderRequest;
import store.streetvendor.core.service.utils.dto.order_history.response.OrderAndHistoryResponse;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @Auth
    @ApiOperation(value = "[사용자] 사용자가 주문한다.")
    @PostMapping("/api/v1/order")
    public ApiResponse<String> addNewOrder(@RequestBody AddNewOrderRequest request, @MemberId Long memberId) {
        orderService.addNewOrder(request, memberId);
        return ApiResponse.OK;
    }



    @Auth
    @ApiOperation(value = "[사용자] 사용자가 주문을 취소한다.")
    @DeleteMapping("/api/v1/orders/{orderId}/cancel")
    public ApiResponse<String> cancelOrderByUser(@MemberId Long memberId, @PathVariable Long orderId) {
        orderService.cancelOrderByUser(orderId, memberId);
        return ApiResponse.OK;
    }



    @Auth
    @ApiOperation(value = "[사용자] 사용자가 모든 주문을 조회한다.")
    @GetMapping("/api/v1/orders")
    public ApiResponse<List<OrderAndHistoryResponse>> allMemberOrders(@MemberId Long memberId) {
        return ApiResponse.success(orderService.getMemberAllOrders(memberId));
    }

}
