package Fujitsu.BACK.courier.domain.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Long> {

    @Query("select s from Station s where s.stationName = ?1 order by s.timestamp DESC")
    List<Station> findTopByStationNameOrderByTimestampDesc(String stationName);



}