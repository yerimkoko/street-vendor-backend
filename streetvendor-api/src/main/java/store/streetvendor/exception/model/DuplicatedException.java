package store.streetvendor.exception.model;

public class DuplicatedException extends CustomException{
    public DuplicatedException(String message) {
        super(message, ErrorCode.DUPLICATED_EXCEPTION);
    }
}
