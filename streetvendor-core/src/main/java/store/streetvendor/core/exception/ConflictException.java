package store.streetvendor.core.exception;

public class ConflictException extends CustomException {
    public ConflictException(String message) {
        super(message, ErrorCode.CONFLICT_EXCEPTION);
    }

}
