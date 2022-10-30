package store.streetvendor.core.service.utils.dto;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @ApiOperation(value = "health check")
    @GetMapping("/ping")
    public ApiResponse<String> test() {
        return ApiResponse.success("pong");
    }

}
