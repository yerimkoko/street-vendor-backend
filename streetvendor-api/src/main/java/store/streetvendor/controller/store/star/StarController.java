package store.streetvendor.controller.store.star;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.Auth;
import store.streetvendor.MemberId;
import store.streetvendor.core.utils.ApiResponse;
import store.streetvendor.core.utils.dto.store.response.projection.StoreAndMemberAndStarResponse;
import store.streetvendor.service.store.StoreService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StarController {

    private final StoreService storeService;

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
    public ApiResponse<String> deleteStar(@PathVariable Long starId, @MemberId Long memberId) {
        storeService.deleteStar(starId, memberId);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "나의 즐겨찾기 가게 조회하기")
    @GetMapping("/api/v1/stars")
    public ApiResponse<List<StoreAndMemberAndStarResponse>> getMyStarStores(@MemberId Long memberId) {
        return ApiResponse.success(storeService.getMyStars(memberId));

    }
}
