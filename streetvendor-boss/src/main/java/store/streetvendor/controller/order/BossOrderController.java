package store.streetvendor.controller.order;

import store.streetvendor.auth.Boss;
import store.streetvendor.auth.BossId;
import store.streetvendor.service.order.BossOrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.core.domain.order.OrderStatus;
import store.streetvendor.core.utils.ApiResponse;
import store.streetvendor.core.utils.dto.order_history.request.AddNewOrderHistoryRequest;
import store.streetvendor.core.utils.dto.order_history.MemberOrderHistoryResponse;
import store.streetvendor.core.utils.dto.order.response.OrderListToBossResponse;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BossOrderController {

    private final BossOrderService bossOrderService;


    @Boss
    @ApiOperation(value = "[사장님] 사장님이 진행중인 주문을 불러온다.")
    @GetMapping("/v1/order")
    public ApiResponse<List<MemberOrderHistoryResponse>> getOrders(@RequestParam(required = false) OrderStatus orderStatus,
                                                                   @RequestParam Long storeId,
                                                                   @BossId Long bossId) {
        return ApiResponse.success(bossOrderService.getOrders(orderStatus, storeId, bossId));

    }


    @Boss
    @ApiOperation(value = "[사장님] 사장님이 orderHistory 를 가져온다")
    @GetMapping("/v1/order/histories")
    public ApiResponse<List<MemberOrderHistoryResponse>> getOrderHistoryList(@RequestParam Long storeId, @BossId Long bossId) {
        return ApiResponse.success(bossOrderService.getOrderHistory(storeId, bossId));
    }

    @Boss
    @ApiOperation(value = "[사장님] 주문 확인하기")
    @GetMapping("/v1/orders/{storeId}")
    public ApiResponse<List<OrderListToBossResponse>> checkOrders(@BossId Long bossId, @PathVariable Long storeId, @RequestParam(required = false) OrderStatus status) {
        return ApiResponse.success(bossOrderService.getAllOrders(storeId, bossId, status));
    }

    @Boss
    @ApiOperation(value = "[사장님] 주문 취소하기")
    @DeleteMapping("/v1/{storeId}/orders/{orderId}/cancel")
    public ApiResponse<String> cancelOrder(@BossId Long memberId, @PathVariable Long storeId, @PathVariable Long orderId) {
        bossOrderService.cancelOrderByBoss(storeId, orderId, memberId);
        return ApiResponse.OK;
    }

    @Boss
    @ApiOperation(value = "[사장님] 사장님이 주문 상태 조리중으로 변경한다.")
    @PutMapping("/v1/orders/preparing/{storeId}/{orderId}")
    public ApiResponse<String> changeStatusToPreparing(@BossId Long memberId, @PathVariable Long storeId, @PathVariable Long orderId) {
        bossOrderService.changeStatusToPreparing(storeId, memberId, orderId);
        return ApiResponse.success("주문의 상태가 (preparing)으로 변경되었습니다.");
    }

    @Boss
    @ApiOperation(value = "[사장님] 사장님이 상태를 pickUp 으로 변경한다.")
    @PutMapping("/v1/orders/readyToPickUp")
    public ApiResponse<String> changeStatusToReadyToPickUp(@BossId Long memberId, @RequestBody AddNewOrderHistoryRequest request) {
        bossOrderService.changeStatusToReadyToPickUp(memberId, request);
        return ApiResponse.success("pick_up 준비가 완료 되었습니다)");
    }


}
