package store.streetvendor.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.core.utils.ApiResponse;
import store.streetvendor.core.auth.GoogleAuthService;
import store.streetvendor.core.config.auth.dto.request.AuthRequest;
import store.streetvendor.core.config.auth.dto.response.AuthResponse;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static store.streetvendor.core.auth.AuthConstants.MEMBER_ID;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final GoogleAuthService googleAuthService;

    private final HttpSession httpSession;

    @Operation(summary = "구글 인증 요청 API", description = "로그인을 위한 토큰 혹은 회원가입을 위한 정보가 반환된다.")
    @PostMapping("/api/v1/auth/google")
    public ApiResponse<AuthResponse> handleGoogleAuthentication(@Valid @RequestBody AuthRequest request) {
        return ApiResponse.success(googleAuthService.handleGoogleAuthentication(request));
    }

    @Operation(summary = "로그아웃", description = "로그아웃이 된다.")
    @PostMapping("/api/v1/log-out")
    public ApiResponse<String> logOut() {
        httpSession.removeAttribute(MEMBER_ID);
        return ApiResponse.OK;
    }

}
