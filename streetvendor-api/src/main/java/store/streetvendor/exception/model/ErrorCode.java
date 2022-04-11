package store.streetvendor.exception.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED_EXCEPTION(ErrorStatusCode.UNAUTHORIZED_EXCEPTION, "인가되지 않은 사용자 입니다. 다시 로그인 해 주세요.");

    private final ErrorStatusCode statusCode;
    private final String message;

    @Getter
    @RequiredArgsConstructor
    private enum ErrorStatusCode {
        VALIDATION_EXCEPTION(400, "VALIDATION_EXCEPTION"),
        UNAUTHORIZED_EXCEPTION(401, "UNAUTHORIZED_EXCEPTION");

        private final int statusCode;
        private final String code;

    }

    public String getCode() {
        return this.statusCode.getCode();
    }
}
