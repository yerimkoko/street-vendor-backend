package store.streetvendor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.controller.dto.ApiResponse;

@RestController
public class TestController {

    @GetMapping("/ping")
    public ApiResponse<String> test() {
        return ApiResponse.success("pong");
    }

}
