package store.streetvendor.controller.order;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.config.auth.Auth;
import store.streetvendor.config.auth.MemberId;
import store.streetvendor.controller.ApiResponse;
import store.streetvendor.service.order.OrderService;
import store.streetvendor.service.order.dto.request.AddNewOrderRequest;
import store.streetvendor.service.order.dto.response.OrderListToBossResponse;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @Auth
    @ApiOperation(value = "주문하기 API")
    @PostMapping("/api/v1/order")
    public ApiResponse<String> addNewOrder(@RequestBody AddNewOrderRequest request, @MemberId Long memberId) {
        orderService.addNewOrder(request, memberId);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "(사장님용) 주문 확인하기 API")
    @GetMapping("/api/v1/orders/{storeId}")
    public ApiResponse<List<OrderListToBossResponse>> checkOrders(@MemberId Long memberId, @PathVariable Long storeId) {
        return ApiResponse.success(orderService.getAllOrders(storeId, memberId));
    }

    @Auth
    @ApiOperation(value = "(사장님용) 주문 취소하기 API")
    @DeleteMapping("/api/v1/{storeId}/orders/{orderId}/cancel")
    public ApiResponse<String> cancelOrder(@MemberId Long memberId, @PathVariable Long storeId, @PathVariable Long orderId) {
        orderService.cancelOrderByBoss(storeId, orderId, memberId);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "(사용자용) 주문 취소하기 API")
    @DeleteMapping("/api/v1/orders/{orderId}/cancel")
    public ApiResponse<String> cancelOrderByUser(@MemberId Long memberId, @PathVariable Long orderId) {
        orderService.cancelOrderByUser(orderId, memberId);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "사장님이 주문 상태 조리중으로 변경한다.")
    @PutMapping("/api/v1/orders/preparing/{storeId}/{orderId}")
    public ApiResponse<String> changeStatusToPreparing(@MemberId Long memberId, @PathVariable Long storeId, @PathVariable Long orderId) {
        orderService.changeStatusToPreparing(storeId, memberId, orderId);
        return ApiResponse.success("주문의 상태가 preparing으로 변경되었습니다.");
    }

}
