package store.streetvendor.controller.auth;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.core.auth.GoogleAuthService;
import store.streetvendor.core.config.auth.dto.request.AuthRequest;
import store.streetvendor.core.config.auth.dto.response.AuthResponse;
import store.streetvendor.core.utils.ApiResponse;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BossAuthController {

    private final GoogleAuthService googleAuthService;

    @ApiOperation(value = "[사장님] 로그인을 한다.")
    @PostMapping("/v1/auth/google")
    public ApiResponse<AuthResponse> handleBossGoogleAuthentication(@Valid @RequestBody AuthRequest request) {
        return ApiResponse.success(googleAuthService.handleGoogleAuthentication(request));
    }

    @ApiOperation(value = "헬스체크")
    @GetMapping("/ping")
    public String pong() {
        return "사장님 서버입니다 pong!";
    }



}
