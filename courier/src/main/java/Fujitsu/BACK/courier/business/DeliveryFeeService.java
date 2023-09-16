package Fujitsu.BACK.courier.business;

import Fujitsu.BACK.courier.entities.Station;
import Fujitsu.BACK.courier.entities.StationRepository;
import Fujitsu.BACK.courier.infrastructure.exception.BusinessException;
import Fujitsu.BACK.courier.validation.ValidationService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Service responsible for calculating delivery fees based on input parameters.
 * It retrieves weather and city information and applies business logic to determine the delivery fee.
 */
@Service
public class DeliveryFeeService {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryFeeService.class);
    @Resource
    private StationRepository stationRepository;

    /**
     * Calculates the delivery fee based on these parameters.
     *
     * @param cityId    The ID of the city.
     * @param vehicleId The ID of the vehicle.
     *                  With cityId get the last Station information from database
     *                  Air temperature, wind speed and weather phenomenon
     *                  According to the business logic given for the exercise, delivery fee will be calculated
     * @return The calculated delivery fee.
     */

    public Double calculateDeliveryFee(Integer cityId, Integer vehicleId) {
        double regionalBaseFee = getRegionalBaseFee(cityId, vehicleId);

        String selectedStationName = getSelectedStationName(cityId);

        List<Station> lastStationRows = stationRepository.findTopByStationNameOrderByTimestampDesc(selectedStationName);
        Station lastStationRow = lastStationRows.get(0);
        double airTemperature = lastStationRow.getAirTemperature();
        double windSpeed = lastStationRow.getWindSpeed();
        String weatherPhenomenon = lastStationRow.getWeatherPhenomenon();

        double airTemperatureExtraFeeCalculation = getAirTemperatureExtraFeeCalculation(vehicleId, airTemperature);

        double windSpeedExtraFeeCalculation = getWindSpeedExtraFeeCalculation(vehicleId, windSpeed);

        double weatherPhenomenonExtraFeeCalculation = getWeatherPhenomenonExtrafeeCalculation(vehicleId, weatherPhenomenon);

        basicInformationToLogger(cityId, vehicleId, selectedStationName, airTemperature, windSpeed, weatherPhenomenon, regionalBaseFee, airTemperatureExtraFeeCalculation, windSpeedExtraFeeCalculation, weatherPhenomenonExtraFeeCalculation);

        return deliveryFeeCalculation(regionalBaseFee, airTemperatureExtraFeeCalculation, windSpeedExtraFeeCalculation, weatherPhenomenonExtraFeeCalculation);

    }

    private static Double deliveryFeeCalculation(double baseFee, double airTemperatureExtraFee, double windSpeedExtraFee, double weatherPhenomenonExtraFeeCalculation) {
        return baseFee + airTemperatureExtraFee + windSpeedExtraFee + weatherPhenomenonExtraFeeCalculation;
    }

    private static void basicInformationToLogger(Integer cityId, Integer vehicleId, String selectedStationName, double airTemperature, double windSpeed, String weatherPhenomenon, double baseFee, double airTemperatureExtraFee, double windSpeedExtraFee, double weatherPhenomenonExtraFeeCalculation) {
        logger.info("cityId: {}", cityId);
        logger.info("vehicleId: {}", vehicleId);
        logger.info("city: {}", selectedStationName);
        logger.info("airTemp: {}", airTemperature);
        logger.info("windspeed: {}", windSpeed);
        logger.info("wetherphen: {}", weatherPhenomenon);
        logger.info("calculating fee RBF: {}", baseFee);
        logger.info("calculating fee ATEF: {}", airTemperatureExtraFee);
        logger.info("calculating fee WSEF: {}", windSpeedExtraFee);
        logger.info("calculating fee WPEF: {}", weatherPhenomenonExtraFeeCalculation);
    }

    private static double getWeatherPhenomenonExtrafeeCalculation(Integer vehicleId, String weatherPhenomenon) {
        double weatherPhenomenonExtraFeeCalculation = 0.0;
        if ((vehicleId.equals(2) || vehicleId.equals(3)) && ((weatherPhenomenon.equalsIgnoreCase("glaze") || weatherPhenomenon.toLowerCase().contains("glaze") || weatherPhenomenon.equalsIgnoreCase("hall") || weatherPhenomenon.toLowerCase().contains("hall") || weatherPhenomenon.equalsIgnoreCase("thunder") || weatherPhenomenon.toLowerCase().contains("thunder")))) {
            try {
                ValidationService.validateWeatherPhenomenonType();
            } catch (BusinessException e) {
                logger.error("An error occurred: {}", e.getMessage());
                return 0.0;
            }
        } else if ((vehicleId.equals(2) || vehicleId.equals(3)) && ((weatherPhenomenon.equalsIgnoreCase("snow") || weatherPhenomenon.toLowerCase().contains("snow") || weatherPhenomenon.equalsIgnoreCase("sleet") || weatherPhenomenon.toLowerCase().contains("sleet")))) {
            weatherPhenomenonExtraFeeCalculation = 1.0;
        } else if ((vehicleId.equals(2) || vehicleId.equals(3)) && (weatherPhenomenon.equalsIgnoreCase("rain") || weatherPhenomenon.toLowerCase().contains("rain"))) {
            weatherPhenomenonExtraFeeCalculation = 0.5;
        } else {
            weatherPhenomenonExtraFeeCalculation = 0.0;
        }
        return weatherPhenomenonExtraFeeCalculation;
    }

    private static double getWindSpeedExtraFeeCalculation(Integer vehicleId, double windSpeed) {
        double windSpeedExtraFee = 0.0;

        if ((vehicleId.equals(3)) && (windSpeed > 20.0)) {
            try {
                ValidationService.validateWeatherPhenomenonType();
            } catch (BusinessException e) {
                logger.error("An error occurred: {}", e.getMessage());
                return 0.0;
            }
        } else if (vehicleId.equals(3) && (windSpeed >= 10.0 && windSpeed <= 20.0)) {
            windSpeedExtraFee = 0.5;
        } else {
            windSpeedExtraFee = 0.0;
        }
        return windSpeedExtraFee;
    }

    private static double getAirTemperatureExtraFeeCalculation(Integer vehicleId, double airTemperature) {
        double airTemperatureExtraFee = 0.0;


        if ((vehicleId.equals(2) || vehicleId.equals(3)) && airTemperature < -10.0) {
            airTemperatureExtraFee = 1.0;
        } else if ((vehicleId.equals(2) || vehicleId.equals(3)) && (airTemperature >= -10.0 && airTemperature <= 0.0)) {
            airTemperatureExtraFee = 0.5;
        } else {
            airTemperatureExtraFee = 0.0;
        }
        return airTemperatureExtraFee;
    }

    private static String getSelectedStationName(Integer cityId) {
        String selectedStationName = "";
        if (cityId.equals(1)) {
            selectedStationName = "Tallinn-Harku";
        } else if (cityId.equals(2)) {
            selectedStationName = "Tartu-Tõravere";
        } else if (cityId.equals(3)) {
            selectedStationName = "Pärnu";
        }
        return selectedStationName;
    }

    private static double getRegionalBaseFee(Integer cityId, Integer vehicleId) {
        double baseFee = 0.0;

        if (cityId.equals(1) && vehicleId.equals(1)) {
            baseFee = 4.0;
        } else if ((cityId.equals(1) && vehicleId.equals(2)) ||
                (cityId.equals(2) && vehicleId.equals(1))) {
            baseFee = 3.5;
        } else if ((cityId.equals(1) && vehicleId.equals(3)) ||
                (cityId.equals(2) && vehicleId.equals(2)) ||
                (cityId.equals(3) && vehicleId.equals(1))) {
            baseFee = 3.0;
        } else if ((cityId.equals(2) && vehicleId.equals(3)) ||
                (cityId.equals(3) && vehicleId.equals(2))) {
            baseFee = 2.5;
        } else if (cityId.equals(3) && vehicleId.equals(3)) {
            baseFee = 2.0;
        }
        return baseFee;
    }
}
