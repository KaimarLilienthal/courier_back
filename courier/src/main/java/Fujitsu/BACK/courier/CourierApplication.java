package Fujitsu.BACK.courier;

import Fujitsu.BACK.courier.business.WeatherDataImportExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@EnableScheduling
@SpringBootApplication
public class CourierApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourierApplication.class, args);
	}
	@Autowired
	private WeatherDataImportExportService weatherDataImportService;

	@Scheduled(cron = "${cron.expression:0 25 * * * ?}")
	public void importWeatherDataJob() throws IOException {
		weatherDataImportService.importWeatherData();
	}


}
