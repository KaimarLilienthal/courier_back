package Fujitsu.BACK.courier.business;

import Fujitsu.BACK.courier.entities.Station;
import Fujitsu.BACK.courier.entities.StationRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;

@Service
public class DeliveryFeeService {

    @Resource
    private StationRepository stationRepository;

    public Double calculateDeliveryFee(Integer cityId, Integer vehicleId) {
        double RBF = 0.0;
        String stationName = "";
        if (cityId.equals(1) && vehicleId.equals(1)) {
            RBF = 4.0;
        } else if ((cityId.equals(1) && vehicleId.equals(2)) || (cityId.equals(2) && vehicleId.equals(1))) {
            RBF = 3.5;
        } else if ((cityId.equals(1) && vehicleId.equals(3)) || (cityId.equals(2) && vehicleId.equals(2)) || (cityId.equals(3) && vehicleId.equals(1))) {
            RBF = 3.0;
        } else if ((cityId.equals(2) && vehicleId.equals(3)) || (cityId.equals(3) && vehicleId.equals(2))) {
            RBF = 2.5;
        } else if (cityId.equals(3) && vehicleId.equals(3)) {
            RBF = 2.0;
        }
        return RBF;
        if (cityId.equals(1)) {
            stationName = "Tallinn-Harku";
        } else if (cityId.equals(2)) {
            stationName = "Tartu-Tõravere";
        } else if (cityId.equals(3)) {
            stationName = "Pärnu";

        }

        Station lastStationRow = stationRepository.findTopByStationNameOrderByTimestampDesc(stationName);
        double airTemperature = lastStationRow.getAirTemperature();
        double windSpeed = lastStationRow.getWindSpeed();
        String weatherPhenomenon = lastStationRow.getWeatherPhenomenon();

        double ATEF = 0.0;
        double WSEF = 0.0;
        double WPEF = 0.0;
        
        if (vehicleId.equals(2) || vehicleId.equals(3) && airTemperature < -10.0) {
            ATEF = 1.0;
        } else if (vehicleId.equals(2) || vehicleId.equals(3) && (airTemperature >= -10.0 && airTemperature <= 0.0)) {
            ATEF = 0.5;
        } else ATEF = 0.0;

        if (vehicleId.equals(3) && (windSpeed >= 10.0 && windSpeed <=20.0)){
            WSEF = 0.5;
        }

        if (vehicleId.equals(2) || vehicleId.equals(3) && (weatherPhenomenon.equals("snow") || weatherPhenomenon.equals("sleet"))){
            WPEF = 1.0;
        } else if (vehicleId.equals(2) || vehicleId.equals(3) && weatherPhenomenon.equals("rain")) {
            WPEF = 0.5;
        }


    }
}
