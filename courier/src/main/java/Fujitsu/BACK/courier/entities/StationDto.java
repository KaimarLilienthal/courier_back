package Fujitsu.BACK.courier.entities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Station}
 */
@Value
public class StationDto implements Serializable {
    @NotNull
    @Size(max = 1000000000)
    String stationName;
    @NotNull
    Double airTemperature;
    @NotNull
    Double windSpeed;
    @NotNull
    @Size(max = 1000000000)
    String weatherPhenomenon;
}