package store.streetvendor.controller.store;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.controller.dto.ApiResponse;
import store.streetvendor.controller.dto.store.StoreDto;
import store.streetvendor.controller.dto.store.StoreUpdateRequest;
import store.streetvendor.service.store.StoreService;
import store.streetvendor.service.store.dto.request.AddNewStoreRequest;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class StoreController {

    private final StoreService storeService;

    // memberId는 차후 변경하고 일단 파라미터로 받음
    @PostMapping("/api/v1/store")
    public ApiResponse<String> addNewStore(@Valid @RequestBody AddNewStoreRequest request, @RequestParam Long memberId) {
        storeService.addNewStore(request, memberId);
        return ApiResponse.OK;
    }

    @GetMapping("/api/v1/store")
    public ApiResponse<List<StoreDto>> getStores(@RequestParam Long memberId) {
        return ApiResponse.success(storeService.getMyStoreList(memberId));
    }

    @PutMapping("/api/v1/store/{storeId}")
    public ApiResponse<String> updateStore(@RequestBody Long memberId, @PathVariable Long storeId,
                                         @RequestBody StoreUpdateRequest request) {
        storeService.updateMyStore(memberId, storeId, request);
        return ApiResponse.OK;
    }

    @DeleteMapping("/api/v1/store/{storeId}")
    public ApiResponse<String> deleteStore(@RequestParam Long memberId, @PathVariable Long storeId) {
        storeService.deleteMyStore(memberId, storeId);
        return ApiResponse.OK;
    }

}
