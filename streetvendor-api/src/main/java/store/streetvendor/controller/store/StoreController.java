package store.streetvendor.controller.store;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.core.config.auth.Auth;
import store.streetvendor.core.config.auth.MemberId;
import store.streetvendor.core.utils.dto.ApiResponse;
import store.streetvendor.core.domain.store.menu.MenuSalesStatus;
import store.streetvendor.service.store.StoreService;
import store.streetvendor.service.store.dto.request.*;
import store.streetvendor.service.store.dto.response.*;
import store.streetvendor.service.store.dto.response.projection.StoreAndMemberAndStarResponse;

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

    @ApiOperation(value = "거리로 부터 전체 가게 조회하기")
    @GetMapping("/api/v1/stores/location")
    public ApiResponse<List<StoreResponse>> closedStoresByLocation(StoreDistanceRequest request) {
        return ApiResponse.success(storeService.getAllStoresByLocation(request));
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
    public ApiResponse<List<StoreResponse>> storesByCategory(StoreCategoryRequest request) {
        return ApiResponse.success(storeService.getStoresByCategoryAndLocationAndStoreStatus(request));
    }

    @ApiOperation(value = "메뉴 상태 수정하기(soldOut, onSales)")
    @PutMapping("/api/v1/store/{storeId}/menu/{menuId}/{menuSalesStatus}")
    public ApiResponse<String> changeMenuStatus(@MemberId Long bossId, @PathVariable Long storeId, @PathVariable Long menuId, @PathVariable MenuSalesStatus menuSalesStatus) {
        storeService.changeMenuStatus(storeId, bossId, menuId, menuSalesStatus);
        return ApiResponse.OK;
    }

    @ApiOperation(value = "리뷰 등록하기")
    @PostMapping("/api/v1/store/review/{storeId}")
    public ApiResponse<String> addNewEvaluation(@MemberId Long memberId, @PathVariable Long storeId, @RequestBody AddStoreReviewRequest request) {
        storeService.addEvaluation(memberId, storeId, request);
        return ApiResponse.OK;
    }

    @ApiOperation(value = "리뷰 수정하기")
    @PutMapping("/api/v1/store/review/{storeId}")
    public ApiResponse<String> updateReview(@MemberId Long memberId, @PathVariable Long storeId, @RequestBody UpdateStoreReviewRequest request) {
        storeService.updateReview(memberId, storeId, request);
        return ApiResponse.OK;
    }

    @ApiOperation(value = "리뷰 삭제하기")
    @DeleteMapping("/api/v1/store/review")
    public ApiResponse<String> deleteReview(@MemberId Long memberId, @RequestParam Long storeId, @RequestParam Long reviewId) {
        storeService.deleteReview(memberId, storeId, reviewId);
        return ApiResponse.OK;
    }

    @ApiOperation(value = "가게 리뷰 조회하기")
    @GetMapping("/api/v1/store/review/{storeId}")
    public ApiResponse<StoreReviewResponse> getStoreReviews(@PathVariable Long storeId) {
        return ApiResponse.success(storeService.getStoreReviews(storeId));
    }

    @ApiOperation(value = "표시된 가게 조회하기")
    @GetMapping("/api/v1/store/{storeId}")
    public ApiResponse<StoreInfoResponse> getStoreInfo(@PathVariable Long storeId) {
        return ApiResponse.success(storeService.getStoreInfo(storeId));
    }


    @Auth
    @ApiOperation(value = "가게 즐겨찾기 추가하기")
    @PostMapping("/api/v1/star")
    public ApiResponse<String> addStar(@MemberId Long memberId, Long storeId) {
        storeService.addStar(memberId, storeId);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "가게 즐겨찾기 삭제하기")
    @DeleteMapping("/api/v1/star/{starId}")
    public ApiResponse<String> deleteStar(@PathVariable Long starId) {
        storeService.deleteStar(starId);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "나의 즐겨찾기 가게 조회하기")
    @GetMapping("/api/v1/stars")
    public ApiResponse<List<StoreAndMemberAndStarResponse>> getMyStarStores(@MemberId Long memberId) {
        return ApiResponse.success(storeService.getMyStars(memberId));
    }


}
