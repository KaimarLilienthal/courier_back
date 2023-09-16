package Fujitsu.BACK.courier.infrastructure.error;

import lombok.Data;
/**
 * Represents an API error message with a message and an error code.
 */
@Data
public class ApiError {
    private String message;
    private Integer errorCode;
}

