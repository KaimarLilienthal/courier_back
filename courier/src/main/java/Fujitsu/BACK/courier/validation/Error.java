package Fujitsu.BACK.courier.validation;

import lombok.Getter;
/**
 * Enumeration of error codes and messages used in the validation service.
 */
@Getter
public enum Error {
    VEHICLE_TYPE_FORBIDDEN("Usage of selected vehicle type is forbidden", 111);

    private final String message;
    private final int errorCode;

    Error(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

}
