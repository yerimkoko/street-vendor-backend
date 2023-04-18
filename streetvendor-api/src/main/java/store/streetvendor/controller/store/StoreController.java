package store.streetvendor.controller.store;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.Auth;
import store.streetvendor.MemberId;
import store.streetvendor.core.domain.store.StoreCategory;
import store.streetvendor.core.utils.ApiResponse;
import store.streetvendor.core.utils.dto.store.MemberLikeStoreListResponse;
import store.streetvendor.core.utils.dto.store.request.*;
import store.streetvendor.core.utils.dto.store.response.*;
import store.streetvendor.service.store.StoreService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StoreController {

    private final StoreService storeService;

    @Value("${cloud.aws.s3.baseUrl}")
    private String baseUrl;

    @Auth
    @ApiOperation(value = "가게 상세 정보 조회하기")
    @GetMapping("/api/v1/store/detail/{storeId}")
    public ApiResponse<StoreDetailResponse> detailResponse(@PathVariable Long storeId) {
        return ApiResponse.success(storeService.getStoreDetail(storeId, baseUrl));
    }


    @ApiOperation(value = "거리로 부터 영업중인 가게 조회하기")
    @GetMapping("/api/v1/stores/location/open")
    public ApiResponse<List<StoreResponse>> openedStoresByLocation(StoreDistanceRequest request) {
        return ApiResponse.success(storeService.getOpenedStoresByLocation(request));
    }


    @ApiOperation(value = "카테고리로 가게 조회하기")
    @GetMapping("/api/v1/store/category/{category}")
    public ApiResponse<List<StoreInfoResponse>> storesByCategory(@PathVariable StoreCategory category,
                                                                 @RequestParam double longitude,
                                                                 @RequestParam double latitude,
                                                                 @RequestParam(required = false) Integer cursor,
                                                                 @RequestParam(required = false, defaultValue = "5") int size) {

        return ApiResponse.success(storeService.getStoresByCategoryAndLocationAndStoreStatus(category, baseUrl, longitude, latitude, cursor, size));
    }



    @ApiOperation(value = "[개발용] 전체 가게 불러오기")
    @GetMapping("/api/v1/stores/dev")
    public ApiResponse<List<StoreDevResponse>> getAllStores() {
        return ApiResponse.success(storeService.getDevStores(baseUrl));
    }

    @Auth
    @ApiOperation(value = "[좋아요] 내가 좋아요 한 가게 가져오기")
    @GetMapping("/api/v1/stores/like")
    public ApiResponse<List<MemberLikeStoreListResponse>> getMemberLikeStores(@MemberId Long memberId,
                                                                              @RequestParam double currentLatitude,
                                                                              @RequestParam double currentLongitude,
                                                                              @RequestParam(required = false) Integer cursor,
                                                                              @RequestParam(required = false, defaultValue = "5") int size) {
        return ApiResponse.success(storeService.getMemberLikeStore(memberId, currentLatitude, currentLongitude, cursor, size));
    }

    @Auth
    @ApiOperation(value = "[좋아요] 가게 좋아요 하기")
    @PostMapping("/api/v1/store/like")
    public ApiResponse<String> memberLikeStore(@MemberId Long memberId,
                                               @RequestParam Long storeId) {

        storeService.addMemberLikeStore(memberId, storeId);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "[좋아요] 취소하기")
    @DeleteMapping("/api/v1/store/like/cancel")
    public ApiResponse<String> cancelMemberLikeStore(@MemberId Long memberId,
                                                     @RequestParam Long storeId) {
        storeService.deleteLikeStore(memberId, storeId);
        return ApiResponse.OK;
    }

}
