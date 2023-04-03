package store.streetvendor.core.exception;

public class InvalidException extends CustomException{
    public InvalidException(String message) {
        super(message, ErrorCode.INVALID_EXCEPTION);
    }
}
