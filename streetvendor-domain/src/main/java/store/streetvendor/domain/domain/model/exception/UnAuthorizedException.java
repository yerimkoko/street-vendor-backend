package store.streetvendor.domain.domain.model.exception;

public class UnAuthorizedException extends CustomException{

    public UnAuthorizedException(String message) {
        super(message, ErrorCode.UNAUTHORIZED_EXCEPTION);
    }
}
