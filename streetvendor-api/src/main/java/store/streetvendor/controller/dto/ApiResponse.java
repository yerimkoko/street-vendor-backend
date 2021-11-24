package store.streetvendor.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    public static final ApiResponse<String> OK = new ApiResponse<>("", "", "성공");

    private final String code;

    private final String message;

    private final T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("", "", data);
    }

    public static ApiResponse<Object> error(String code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}
