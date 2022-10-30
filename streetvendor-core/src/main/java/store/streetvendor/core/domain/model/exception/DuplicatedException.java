package store.streetvendor.core.domain.model.exception;

public class DuplicatedException extends CustomException{
    public DuplicatedException(String message) {
        super(message, ErrorCode.DUPLICATED_EXCEPTION);
    }
}
