package store.streetvendor.exception.model;

public class AlreadyExistedException extends CustomException{

    public AlreadyExistedException(String message) {
        super(message, ErrorCode.ALREADY_EXISTED_EXCEPTION);
    }
}
