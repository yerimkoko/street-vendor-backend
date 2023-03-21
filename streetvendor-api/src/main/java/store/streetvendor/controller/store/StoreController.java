package store.streetvendor.controller.store;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.Auth;
import store.streetvendor.MemberId;
import store.streetvendor.core.domain.store.StoreCategory;
import store.streetvendor.core.utils.ApiResponse;
import store.streetvendor.core.domain.store.menu.MenuSalesStatus;
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

    @ApiOperation(value = "[테스트용] 전체 가게 조회하기")
    @GetMapping("/api/v1/stores")
    public ApiResponse<List<StoreSimpleResponse>> allStores(@RequestParam int size, @RequestParam int lastId) {
        return ApiResponse.success(storeService.getAllStoreList(size, lastId));
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

    @Auth
    @ApiOperation(value = "메뉴 상태 수정하기(soldOut, onSales)")
    @PutMapping("/api/v1/store/{storeId}/menu/{menuId}/{menuSalesStatus}")
    public ApiResponse<String> changeMenuStatus(@MemberId Long bossId, @PathVariable Long storeId, @PathVariable Long menuId, @PathVariable MenuSalesStatus menuSalesStatus) {
        storeService.changeMenuStatus(storeId, bossId, menuId, menuSalesStatus);
        return ApiResponse.OK;
    }

    @ApiOperation(value = "표시된 가게 조회하기")
    @GetMapping("/api/v1/store/{storeId}")
    public ApiResponse<StoreInfoResponse> getStoreInfo(@PathVariable Long storeId) {
        return ApiResponse.success(storeService.getStoreInfo(storeId));
    }


    @Auth
    @ApiOperation(value = "인기 가게와 인기 메뉴들")
    @GetMapping("/api/v1/stores/menus")
    public ApiResponse<PopularStoresAndMenusResponse> getStoresAndMenus(@RequestParam Double latitude,
                                                                        @RequestParam Double longitude) {
        return ApiResponse.success(storeService.popularStoresAndMenus(latitude, longitude));
    }

    @ApiOperation(value = "[개발용] 전체 가게 불러오기")
    @GetMapping("/api/v1/stores/dev")
    public ApiResponse<List<StoreDevResponse>> getAllStores() {
        return ApiResponse.success(storeService.getDevStores());
    }

}
