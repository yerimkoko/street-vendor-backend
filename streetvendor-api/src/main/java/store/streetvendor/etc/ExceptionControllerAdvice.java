package store.streetvendor.etc;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import store.streetvendor.controller.ApiResponse;

import java.net.BindException;

import static store.streetvendor.etc.ErrorCode.UNAUTHORIZED_EXCEPTION;
import static store.streetvendor.etc.ErrorCode.VALIDATION_EXCEPTION;


@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String field = e.getBindingResult().getFieldError() == null ? "" : e.getBindingResult().getFieldError().getField();
        return ApiResponse.error(UNAUTHORIZED_EXCEPTION.getCode(), String.format("%s - %s", field, e.getBindingResult().getFieldError().getDefaultMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    protected ApiResponse<Object> handleBadRequest(BindException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(VALIDATION_EXCEPTION.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    protected ApiResponse<Object> handleMethodArgumentMismatchException(InvalidFormatException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(UNAUTHORIZED_EXCEPTION.getCode(), e.getMessage());
    }

    // Custom Exception
    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Object> handleUnAuthorizedException(UnAuthorizedException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(e.getErrorCode().getCode(), e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Object> handleNotFoundException(NotFoundException e) {
        log.error(e.getErrorCode().getMessage(), e);
        return ApiResponse.error(e.getErrorCode().getCode(), e.getMessage());
    }

    @ExceptionHandler(DuplicatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Object> handleDuplicatedException(DuplicatedException e) {
        log.error(e.getErrorCode().getCode(), e);
        return ApiResponse.error(e.getErrorCode().getCode(), e.getMessage());
    }

    @ExceptionHandler(AlreadyExistedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Object> handleAlreadyExistedException(AlreadyExistedException e) {
        log.error(e.getErrorCode().getCode(), e);
        return ApiResponse.error(e.getErrorCode().getCode(), e.getMessage());
    }

}
