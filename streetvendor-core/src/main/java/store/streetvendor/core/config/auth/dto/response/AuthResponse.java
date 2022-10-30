package store.streetvendor.core.config.auth.dto.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthResponse {

    private AuthType type;

    private String email;

    private String name;

    private String profileUrl;

    private String sessionId;

    public static AuthResponse signUp(String email, String name, String profileUrl) {
        return new AuthResponse(AuthType.SIGN_UP, email, name, profileUrl, null);
    }

    public static AuthResponse logIn(String sessionId) {
        return new AuthResponse(AuthType.LOGIN,null,null,null, sessionId);
    }

    public enum AuthType {
        SIGN_UP,
        LOGIN
    }

}
