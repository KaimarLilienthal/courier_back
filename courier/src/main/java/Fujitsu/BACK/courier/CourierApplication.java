package Fujitsu.BACK.courier;

import Fujitsu.BACK.courier.business.WeatherDataImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.time.Instant;

@EnableScheduling
@SpringBootApplication
public class CourierApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourierApplication.class, args);
	}
	@Autowired
	private WeatherDataImportService weatherDataImportService;

	@Scheduled(cron = "${cron.expression:0 52 * * * ?}")
	public void importWeatherDataJob() throws IOException {
		final Logger logger = LoggerFactory.getLogger(CourierApplication.class);

		logger.info("Scheduled job started at {}", Instant.now());
		weatherDataImportService.importWeatherData();
		logger.info("Scheduled job completed at {}", Instant.now());
	}


}
