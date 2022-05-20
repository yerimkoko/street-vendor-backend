package store.streetvendor.controller.store;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.config.auth.Auth;
import store.streetvendor.config.auth.MemberId;
import store.streetvendor.controller.ApiResponse;
import store.streetvendor.service.store.dto.request.StoreCategoryRequest;
import store.streetvendor.service.store.dto.request.StoreDistanceRequest;
import store.streetvendor.service.store.dto.response.MyStoreInfo;
import store.streetvendor.service.store.dto.response.StoreDetailResponse;
import store.streetvendor.service.store.dto.response.StoreResponseDto;
import store.streetvendor.service.store.dto.request.StoreUpdateRequest;
import store.streetvendor.service.store.StoreService;
import store.streetvendor.service.store.dto.request.AddNewStoreRequest;
import store.streetvendor.service.store.dto.response.StoreSimpleResponse;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class StoreController {

    private final StoreService storeService;

    @Auth
    @ApiOperation(value = "(사장님 정보가 있을 때) 창업하기")
    @PostMapping("/api/v1/store")
    public ApiResponse<String> addNewStore(@Valid @RequestBody AddNewStoreRequest request, @MemberId Long memberId) {
        storeService.addNewStore(request, memberId);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "내 가게 정보 불러오기")
    @GetMapping("/api/v1/my-stores")
    public ApiResponse<List<MyStoreInfo>> getMyStores(@MemberId Long memberId) {
        return ApiResponse.success(storeService.getMyStores(memberId));
    }

    @Auth
    @ApiOperation(value = "내 가게 정보 업데이트 하기")
    @PutMapping("/api/v1/store/{storeId}")
    public ApiResponse<String> updateStore(@MemberId Long memberId, @PathVariable Long storeId,
                                           @Valid @RequestBody StoreUpdateRequest request) {
        storeService.updateMyStore(memberId, storeId, request);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "가게 삭제하기")
    @DeleteMapping("/api/v1/store/{storeId}")
    public ApiResponse<String> deleteStore(@MemberId Long memberId, @PathVariable Long storeId) {
        storeService.deleteMyStore(memberId, storeId);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "가게 상세정보 조회하기")
    @GetMapping("/api/v1/store/detail/{storeId}")
    public ApiResponse<StoreDetailResponse> detailResponse(@PathVariable Long storeId) {
        return ApiResponse.success(storeService.getStoreDetail(storeId));
    }

    @ApiOperation(value = "전체 가게 조회하기")
    @GetMapping("/api/v1/stores")
    public ApiResponse<List<StoreSimpleResponse>> allStores(@RequestParam int size, @RequestParam int lastId) {
        return ApiResponse.success(storeService.getAllStoreList(size, lastId));
    }

    @ApiOperation(value = "거리로 부터 영업중인 가게 조회하기")
    @GetMapping("/api/v1/stores/location/open")
    public ApiResponse<List<StoreResponseDto>> openedStoresByLocation(StoreDistanceRequest request) {
        return ApiResponse.success(storeService.getOpenedStoreByLocation(request));
    }

    @ApiOperation(value = "거리로 부터 영업 종료된 가게 조회하기")
    @GetMapping("/api/v1/stores/location/closed")
    public ApiResponse<List<StoreResponseDto>> closedStoresByLocation(StoreDistanceRequest request) {
        return ApiResponse.success(storeService.getClosedStoreByLocation(request));
    }

    @Auth
    @ApiOperation(value = "가게 영업 시작하기")
    @PutMapping("/api/v1/store/sales-status/open/{storeId}")
    public ApiResponse<String> storeOpen(@MemberId Long memberId, @PathVariable Long storeId) {
        storeService.storeOpen(memberId, storeId);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "가게 영업 종료하기")
    @PutMapping("/api/v1/store/sales-status/closed/{storeId}")
    public ApiResponse<String> storeClosed(@MemberId Long memberId, @PathVariable Long storeId) {
        storeService.storeClose(memberId, storeId);
        return ApiResponse.OK;
    }

    @ApiOperation(value = "카테고리로 가게 조회하기")
    @GetMapping("/api/v1/store/{category}")
    public ApiResponse<List<StoreResponseDto>> storesByCategory(StoreCategoryRequest request) {
        return ApiResponse.success(storeService.getStoreByCategory(request));
    }

}
