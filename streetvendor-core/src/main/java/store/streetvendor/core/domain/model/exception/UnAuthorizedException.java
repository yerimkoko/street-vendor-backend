package store.streetvendor.core.domain.model.exception;

public class UnAuthorizedException extends CustomException{

    public UnAuthorizedException(String message) {
        super(message, ErrorCode.UNAUTHORIZED_EXCEPTION);
    }
}
