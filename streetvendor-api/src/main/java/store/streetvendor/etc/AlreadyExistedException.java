package store.streetvendor.etc;

public class AlreadyExistedException extends CustomException{

    public AlreadyExistedException(String message) {
        super(message, ErrorCode.ALREADY_EXISTED_EXCEPTION);
    }
}
