package store.streetvendor.controller.orderHistory;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.config.auth.Auth;
import store.streetvendor.config.auth.MemberId;
import store.streetvendor.controller.ApiResponse;
import store.streetvendor.service.order_history.OrderHistoryService;
import store.streetvendor.service.order_history.dto.response.OrderHistoryResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;

    @Auth
    @ApiOperation(value = "OrderHisotry 가져오기")
    @GetMapping("/api/v1/order-history/{storeId}")
    public ApiResponse<List<OrderHistoryResponse>> getOrderHistoryList(@PathVariable Long storeId, @MemberId Long bossId) {
        return ApiResponse.success(orderHistoryService.getOrderHistory(storeId, bossId));
    }


}
