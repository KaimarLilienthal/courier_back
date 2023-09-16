package Fujitsu.BACK.courier.infrastructure;

import Fujitsu.BACK.courier.infrastructure.error.ApiError;
import Fujitsu.BACK.courier.infrastructure.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
/**
 * Global exception handler for REST controllers.
 * This class handles exceptions of type {@link BusinessException} and returns appropriate error responses.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Handle a business exception and return a response entity with an {@link ApiError} body.
     *
     * @param exception The business exception to handle.
     * @return A ResponseEntity containing an ApiError with error details and HTTP status code FORBIDDEN (403).
     */
    @ExceptionHandler
    public ResponseEntity<ApiError> handleBusinessException(BusinessException exception) {
        ApiError apiError = new ApiError();
        apiError.setMessage(exception.getMessage());
        apiError.setErrorCode(exception.getErrorCode());
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

}
