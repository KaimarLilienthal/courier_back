package Fujitsu.BACK.courier.business;

import org.springframework.stereotype.Service;

@Service
public class DeliveryFeeService {

    public Double calculateDeliveryFee(Integer cityId, Integer vehicleId) {
        Double RBF = 0.0;
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
    }
}
