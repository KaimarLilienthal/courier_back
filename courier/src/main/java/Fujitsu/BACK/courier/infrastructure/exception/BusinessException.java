package Fujitsu.BACK.courier.infrastructure.exception;

import lombok.Data;
/**
 * Exception class representing a business-related error.
 */
@Data
public class BusinessException extends RuntimeException {
    private final String message;
    private final Integer errorCode;
    /**
     * Constructs a new BusinessException with the specified error message and error code.
     *
     * @param message   The error message describing the issue.
     * @param errorCode The error code associated with the issue.
     */
    public BusinessException(String message, Integer errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }
}
