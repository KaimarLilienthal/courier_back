package Fujitsu.BACK.courier.business;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery")
public class DeliveryFeeController {

    @Resource
    private DeliveryFeeService deliveryFeeService;

    @GetMapping("/fee")
    @Operation(summary = "Calculating delivery fee according to input parameters" +
            "(RBF will calculated by city and vehicle type) + ATEF, WSEF, WPEF and returning it",
            description = "ATEF-air temperature, WSEF-wind speed, WPEF-weather phenomenon" +
                    "cityId's: Tallinn=1, Tartu=2, Pärnu=3" +
                    "vehicleId's: car=1, scooter=2, bike=3")
    public void getDeliveryFee(@RequestParam Integer cityId, @RequestParam Integer vehicleId){
        Double fee = deliveryFeeService.calculateDeliveryFee(cityId, vehicleId);
    }

}
