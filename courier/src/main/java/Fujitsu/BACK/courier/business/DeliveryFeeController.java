package Fujitsu.BACK.courier.business;

import Fujitsu.BACK.courier.infrastructure.error.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "111", description = "Usage of selected vehicle type is forbidden", content = @Content(schema = @Schema(implementation = ApiError.class)))})
    public Double getDeliveryFee(@RequestParam Integer cityId, @RequestParam Integer vehicleId){
        return deliveryFeeService.calculateDeliveryFee(cityId, vehicleId);

    }

}
