package Fujitsu.BACK.courier.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;

public interface StationRepository extends JpaRepository<Station, Long> {

    @Query("select s from Station s where s.stationName = ?1 order by s.timestamp DESC")
    Station findTopByStationNameOrderByTimestampDesc(String stationName);



}