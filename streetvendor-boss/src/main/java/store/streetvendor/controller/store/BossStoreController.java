package store.streetvendor.controller.store;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.auth.Boss;
import store.streetvendor.auth.BossId;
import store.streetvendor.core.utils.dto.ApiResponse;
import store.streetvendor.core.utils.dto.store.request.StoreUpdateRequest;
import store.streetvendor.service.BossStoreService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BossStoreController {

    private final BossStoreService bossStoreService;

    @Boss
    @ApiOperation(value = "내 가게 정보 업데이트 하기")
    @PutMapping("/api/v1/store/{storeId}")
    public ApiResponse<String> updateStore(@BossId Long memberId, @PathVariable Long storeId,
                                           @Valid @RequestBody StoreUpdateRequest request) {
        bossStoreService.updateMyStore(memberId, storeId, request);
        return ApiResponse.OK;
    }

    @Boss
    @ApiOperation(value = "가게 삭제하기")
    @DeleteMapping("/api/v1/store/{storeId}")
    public ApiResponse<String> deleteStore(@BossId Long memberId, @PathVariable Long storeId) {
        bossStoreService.deleteMyStore(memberId, storeId);
        return ApiResponse.OK;
    }

}
