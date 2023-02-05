package store.streetvendor.core.exception;

public class BadRequestException extends CustomException {
    public BadRequestException(String message) {
        super(message, ErrorCode.BAD_REQUEST);
    }
}
