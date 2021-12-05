package store.streetvendor.controller.order;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
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
        return ApiResponse.success(orderService.allOrderList(storeId, memberId));
    }
}
