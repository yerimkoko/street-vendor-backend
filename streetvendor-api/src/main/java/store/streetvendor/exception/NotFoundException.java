package store.streetvendor.exception;

public class NotFoundException extends CustomException{
    public NotFoundException(String message) {
        super(message, ErrorCode.NOT_FOUND_EXCEPTION);
    }
}
