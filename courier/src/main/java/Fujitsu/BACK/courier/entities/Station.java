package Fujitsu.BACK.courier.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
@Data
@Getter
@Setter
@Entity
@Table(name = "STATIONS")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotNull
    @Lob
    @Column(name = "STATION_NAME", nullable = false)
    private String stationName;

    @NotNull
    @Column(name = "WMO_CODE", nullable = false)
    private Integer wmoCode;

    @NotNull
    @Column(name = "AIT_TEMPERATURE", nullable = false)
    private Double aitTemperature;

    @NotNull
    @Column(name = "WIND_SPEED", nullable = false)
    private Double windSpeed;

    @NotNull
    @Lob
    @Column(name = "WEATHER_PHENOMENON", nullable = false)
    private String weatherPhenomenon;

    @CreationTimestamp
    @Column(name = "TIMESTAMP", nullable = false)
    private Instant timestamp;

}