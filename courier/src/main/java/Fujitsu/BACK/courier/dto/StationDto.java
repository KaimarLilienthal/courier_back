package Fujitsu.BACK.courier.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link Fujitsu.BACK.courier.entities.Station}
 */
@Data
@AllArgsConstructor

@Value
public class StationDto implements Serializable {
    @NotNull
    String stationName;
    @NotNull
    Integer wmoCode;
    @NotNull
    Double aitTemperature;
    @NotNull
    Double windSpeed;
    @NotNull
    String weatherPhenomenon;
    Instant timestamp;
}