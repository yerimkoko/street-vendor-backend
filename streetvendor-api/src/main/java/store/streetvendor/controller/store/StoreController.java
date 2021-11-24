package store.streetvendor.controller.store;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.controller.dto.ApiResponse;
import store.streetvendor.service.store.StoreService;
import store.streetvendor.service.store.dto.request.AddNewStoreRequest;

import javax.validation.Valid;

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

}
