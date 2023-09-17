-- for H2

CREATE TABLE IF NOT EXISTS stations
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    station_name       VARCHAR NOT NULL,
    wmo_code           INTEGER NOT NULL,
    air_temperature    DOUBLE NOT NULL,
    wind_speed         DOUBLE NOT NULL,
    weather_phenomenon VARCHAR NOT NULL,
    timestamp        TIMESTAMP NOT NULL
);
