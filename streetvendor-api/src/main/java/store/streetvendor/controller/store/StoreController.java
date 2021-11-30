package store.streetvendor.controller.store;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.config.auth.Auth;
import store.streetvendor.config.auth.MemberId;
import store.streetvendor.controller.ApiResponse;
import store.streetvendor.service.store.dto.response.StoreResponseDto;
import store.streetvendor.service.store.dto.request.StoreUpdateRequest;
import store.streetvendor.service.store.StoreService;
import store.streetvendor.service.store.dto.request.AddNewStoreRequest;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class StoreController {

    private final StoreService storeService;

    @Auth
    @PostMapping("/api/v1/store")
    public ApiResponse<String> addNewStore(@Valid @RequestBody AddNewStoreRequest request, @MemberId Long memberId) {
        storeService.addNewStore(request, memberId);
        return ApiResponse.OK;
    }

    @Auth
    @GetMapping("/api/v1/my-stores")
    public ApiResponse<List<StoreResponseDto>> getMyStores(@MemberId Long memberId) {
        return ApiResponse.success(storeService.getMyStoreList(memberId));
    }

    @Auth
    @PutMapping("/api/v1/store/{storeId}")
    public ApiResponse<String> updateStore(@MemberId Long memberId, @PathVariable Long storeId,
                                           @Valid @RequestBody StoreUpdateRequest request) {
        storeService.updateMyStore(memberId, storeId, request);
        return ApiResponse.OK;
    }

    @Auth
    @DeleteMapping("/api/v1/store/{storeId}")
    public ApiResponse<String> deleteStore(@MemberId Long memberId, @PathVariable Long storeId) {
        storeService.deleteMyStore(memberId, storeId);
        return ApiResponse.OK;
    }

}
