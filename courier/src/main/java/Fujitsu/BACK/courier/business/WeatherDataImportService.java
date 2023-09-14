package Fujitsu.BACK.courier.business;

import Fujitsu.BACK.courier.domain.StationRepository;
import Fujitsu.BACK.courier.entities.Station;
import jakarta.annotation.Resource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.Instant;



@Service
public class WeatherDataImportService {
    @Resource
    private final StationRepository stationRepository;


    public WeatherDataImportService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }
    private final Logger logger = LoggerFactory.getLogger(WeatherDataImportService.class);
    @Transactional
    public void importWeatherData() throws IOException {
        try {
            String url = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
            Document document = Jsoup.connect(url).get();

            // Parse and process the XML data as needed.
            Elements stationElements = document.select("station");

            for (Element stationElement : stationElements) {
                String stationName = stationElement.select("name").text();
                if (isDesiredStation(stationName)) {
                    Station station = new Station();
                    station.setStationName(stationName);
                    station.setWmoCode(Integer.parseInt(stationElement.select("wmocode").text()));
                    station.setAitTemperature(Double.parseDouble(stationElement.select("airtemperature").text()));
                    station.setWindSpeed(Double.parseDouble(stationElement.select("windspeed").text()));
                    station.setWeatherPhenomenon(stationElement.select("phenomenon").text());
                    station.setTimestamp(Instant.now());

                    // Save the station to the database
                    stationRepository.save(station);
                    logger.info("Station saved: {}", station);
                }
            }
        } catch (SocketTimeoutException e) {
            // Handle network-related exception
            logger.error("Network issue while connecting to the URL: {}", e.getMessage(), e);
        } catch (NumberFormatException e) {
            // Handle data conversion exception
            logger.error("Error while parsing data: {}", e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            // Handle data repository exception (JPA)
            logger.error("Data repository issue: {}", e.getMessage(), e);
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("Error while saving station: {}", e.getMessage(), e);
        }

    }

    private boolean isDesiredStation(String stationName) {
        return "Tallinn-Harku".equalsIgnoreCase(stationName) ||
                "Pärnu".equalsIgnoreCase(stationName) ||
                "Tartu-Tõravere".equalsIgnoreCase(stationName);
    }
}