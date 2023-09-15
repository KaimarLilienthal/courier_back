-- for postgres

CREATE TABLE stations
(
    id                 SERIAL PRIMARY KEY,
    station_name       VARCHAR NOT NULL,
    wmo_code           INTEGER NOT NULL,
    air_temperature    DOUBLE PRECISION NOT NULL,
    wind_speed         DOUBLE PRECISION NOT NULL,
    weather_phenomenon VARCHAR NOT NULL,
    timestamp          TIMESTAMP NOT NULL
);
