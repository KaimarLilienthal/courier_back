package Fujitsu.BACK.courier.business;

import Fujitsu.BACK.courier.entities.Station;
import Fujitsu.BACK.courier.entities.StationRepository;
import jakarta.annotation.Resource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Service responsible for importing weather data from an external source
 * and storing it in the application's database.
 */
@Service
public class WeatherDataImportExportService {
    @Resource
    private final StationRepository stationRepository;


    public WeatherDataImportExportService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(WeatherDataImportExportService.class);

    /**
     * Imports weather data from an external source and saves it to the database.
     *
     * @throws IOException If there is an error while fetching or parsing the weather data.
     */
    @Transactional
    public void importWeatherData() throws IOException {
        try {
            Document document = weatherDataImport();

            Elements stationElements = document.select("station");

            createStationfromElement(stationElements);
        } catch (Exception e) {
            logger.error("Error while saving station: {}", e.getMessage(), e);
        }

    }

    private static Document weatherDataImport() throws IOException {
        String weatherDataUrl = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
        Document document = Jsoup.connect(weatherDataUrl).get();
        return document;
    }

    private boolean isDesiredStation(String stationName) {
        return "Tallinn-Harku".equalsIgnoreCase(stationName) ||
                "Pärnu".equalsIgnoreCase(stationName) ||
                "Tartu-Tõravere".equalsIgnoreCase(stationName);
    }

    private void createStationfromElement(Elements stationElements) {
        for (Element stationElement : stationElements) {
            String stationName = stationElement.select("name").text();
            if (isDesiredStation(stationName)) {
                Station station = new Station();
                station.setStationName(stationName);
                station.setWmoCode(Integer.parseInt(stationElement.select("wmocode").text()));
                station.setAirTemperature(Double.parseDouble(stationElement.select("airtemperature").text()));
                station.setWindSpeed(Double.parseDouble(stationElement.select("windspeed").text()));
                station.setWeatherPhenomenon(stationElement.select("phenomenon").text());
                station.setTimestamp(Instant.now().plus(3, ChronoUnit.HOURS));

                stationRepository.save(station);
            }
        }
    }
}