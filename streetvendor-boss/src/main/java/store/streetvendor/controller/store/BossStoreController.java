package store.streetvendor.controller.store;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.streetvendor.core.auth.Auth;
import store.streetvendor.core.auth.MemberId;
import store.streetvendor.core.aws.response.ImageUrlResponse;
import store.streetvendor.core.exception.InvalidException;
import store.streetvendor.core.utils.ApiResponse;
import store.streetvendor.core.utils.dto.store.request.AddNewStoreRequest;
import store.streetvendor.core.utils.dto.store.request.StoreUpdateRequest;
import store.streetvendor.service.store.BossStoreService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BossStoreController {

    private final BossStoreService bossStoreService;

    @Auth
    @ApiOperation(value = "내 가게 정보 업데이트 하기")
    @PutMapping("/api/v1/store/{storeId}")
    public ApiResponse<String> updateStore(@MemberId Long memberId, @PathVariable Long storeId,
                                           @Valid @RequestBody StoreUpdateRequest request) {
        bossStoreService.updateMyStore(memberId, storeId, request);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "가게 삭제하기")
    @DeleteMapping("/api/v1/store/{storeId}")
    public ApiResponse<String> deleteStore(@MemberId Long memberId, @PathVariable Long storeId) {
        bossStoreService.deleteMyStore(memberId, storeId);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "[사장님] 창업을 합니다.")
    @PostMapping("/v1/create-store")
    public ApiResponse<String> addNewStore(@Valid @RequestBody AddNewStoreRequest request, @MemberId Long bossId) {
        bossStoreService.addNewStore(request, bossId);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "[사장님] 가게 이미지를 추가합니다.")
    @PostMapping("/v1/store/images")
    public ApiResponse<List<ImageUrlResponse>> addStoreImages(@RequestPart List<MultipartFile> storeImages) {
        if (storeImages.isEmpty()) {
            throw new InvalidException("가게 이미지를 추가해주세요");
        }
        return ApiResponse.success(bossStoreService.addStoreImage(storeImages));
    }

}
