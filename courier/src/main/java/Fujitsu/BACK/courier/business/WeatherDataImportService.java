package Fujitsu.BACK.courier.business;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WeatherDataImportService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDataImportService.class);

    public void importWeatherData() {
        try {
            String url = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
            Document document = Jsoup.connect(url).get();

            // Parse and process the XML data as needed.
            // You can use Jsoup's methods to extract data from the document.

            // For example, you can log the entire XML document to the console:
            logger.info("Weather Data: \n" + document.outerHtml());

            // Or you can extract specific data and log it:
            String temperature = document.select("temperature").text();
            String humidity = document.select("humidity").text();

            logger.info("Temperature: " + temperature);
            logger.info("Humidity: " + humidity);

            // You can choose what to add to a database table based on your logic.
        } catch (Exception e) {
            // Handle exceptions
            logger.error("Error importing weather data", e);
        }
    }
}


