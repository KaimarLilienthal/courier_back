package Fujitsu.BACK.courier.validation;


import Fujitsu.BACK.courier.infrastructure.exception.BusinessException;
import org.springframework.stereotype.Service;
/**
 * Service responsible for validation operations related to weather phenomenon types.
 */
@Service
public class ValidationService {
    /**
     * Validates the weather phenomenon type.
     *
     * @throws BusinessException If the selected weather phenomenon type is forbidden.
     */
    public static void validateWeatherPhenomenonType() {
        throw new BusinessException(Error.VEHICLE_TYPE_FORBIDDEN.getMessage(), Error.VEHICLE_TYPE_FORBIDDEN.getErrorCode());
    }

}
