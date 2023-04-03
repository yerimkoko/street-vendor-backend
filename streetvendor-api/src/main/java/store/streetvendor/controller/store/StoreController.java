package store.streetvendor.controller.store;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.Auth;
import store.streetvendor.core.domain.store.StoreCategory;
import store.streetvendor.core.utils.ApiResponse;
import store.streetvendor.core.utils.dto.store.request.*;
import store.streetvendor.core.utils.dto.store.response.*;
import store.streetvendor.service.store.StoreService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StoreController {

    private final StoreService storeService;

    @Auth
    @ApiOperation(value = "가게 상세 정보 조회하기")
    @GetMapping("/api/v1/store/detail/{storeId}")
    public ApiResponse<StoreDetailResponse> detailResponse(@PathVariable Long storeId) {
        return ApiResponse.success(storeService.getStoreDetail(storeId));
    }


    @ApiOperation(value = "거리로 부터 영업중인 가게 조회하기")
    @GetMapping("/api/v1/stores/location/open")
    public ApiResponse<List<StoreResponse>> openedStoresByLocation(StoreDistanceRequest request) {
        return ApiResponse.success(storeService.getOpenedStoresByLocation(request));
    }

    @ApiOperation(value = "거리로부터 전체 가게 조회하기")
    @GetMapping("/api/v1/stores/location")
    public ApiResponse<List<StoreResponse>> closedStoresByLocation(StoreDistanceRequest request) {
        return ApiResponse.success(storeService.getAllStoresByLocation(request));
    }

    @ApiOperation(value = "카테고리로 가게 조회하기")
    @GetMapping("/api/v1/store/category/{category}")
    public ApiResponse<List<StoreResponse>> storesByCategory(@RequestBody StoreCategoryRequest request, @PathVariable StoreCategory category) {
        return ApiResponse.success(storeService.getStoresByCategoryAndLocationAndStoreStatus(request, category));
    }

    @ApiOperation(value = "표시된 가게 조회하기")
    @GetMapping("/api/v1/store/{storeId}")
    public ApiResponse<StoreInfoResponse> getStoreInfo(@PathVariable Long storeId) {
        return ApiResponse.success(storeService.getStoreInfo(storeId));
    }


    @ApiOperation(value = "[개발용] 전체 가게 불러오기")
    @GetMapping("/api/v1/stores/dev")
    public ApiResponse<List<StoreDevResponse>> getAllStores() {
        return ApiResponse.success(storeService.getDevStores());
    }

}
