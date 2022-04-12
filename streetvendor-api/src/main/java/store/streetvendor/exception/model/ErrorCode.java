package store.streetvendor.exception.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    VALIDATION_EXCEPTION(ErrorStatusCode.VALIDATION_EXCEPTION, "입력이 잘못되었습니다."),
    UNAUTHORIZED_EXCEPTION(ErrorStatusCode.UNAUTHORIZED_EXCEPTION, "인가되지 않은 사용자 입니다. 다시 로그인 해 주세요."),
    NOT_FOUND_EXCEPTION(ErrorStatusCode.NOTFOUND_EXCEPTION, "해당하는 페이지를 찾을 수 었습니다."),
    DUPLICATED_EXCEPTION(ErrorStatusCode.CONFLICT, "중복되는 에러 입니다.");

    private final ErrorStatusCode statusCode;
    private final String message;

    @Getter
    @RequiredArgsConstructor
    private enum ErrorStatusCode {
        VALIDATION_EXCEPTION(400, "VALIDATION_EXCEPTION"),
        UNAUTHORIZED_EXCEPTION(401, "UNAUTHORIZED_EXCEPTION"),
        NOTFOUND_EXCEPTION(404, "NOTFOUND_EXCEPTION"),
        CONFLICT(409, "CONFLICT_EXCEPTION");

        private final int statusCode;
        private final String code;

    }

    public String getCode() {
        return this.statusCode.getCode();
    }
}
