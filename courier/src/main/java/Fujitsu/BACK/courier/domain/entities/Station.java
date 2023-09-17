package Fujitsu.BACK.courier.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "STATIONS")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Size(max = 1000000000)
    @NotNull
    @Column(name = "STATION_NAME", nullable = false, length = 1000000000)
    private String stationName;

    @NotNull
    @Column(name = "WMO_CODE", nullable = false)
    private Integer wmoCode;

    @NotNull
    @Column(name = "AIR_TEMPERATURE", nullable = false)
    private Double airTemperature;

    @NotNull
    @Column(name = "WIND_SPEED", nullable = false)
    private Double windSpeed;

    @Size(max = 1000000000)
    @NotNull
    @Column(name = "WEATHER_PHENOMENON", nullable = false, length = 1000000000)
    private String weatherPhenomenon;

    @NotNull
    @Column(name = "TIMESTAMP", nullable = false)
    private Instant timestamp;

}