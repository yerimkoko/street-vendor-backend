package store.streetvendor.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.controller.dto.ApiResponse;

@Configuration
@RestController
public class TestController {

    @ApiOperation(value = "health check")
    @GetMapping("/ping")
    public ApiResponse<String> test() {
        return ApiResponse.success("pong");
    }

}
