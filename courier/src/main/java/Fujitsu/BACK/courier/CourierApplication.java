package Fujitsu.BACK.courier;

import Fujitsu.BACK.courier.business.WeatherDataImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class CourierApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourierApplication.class, args);
	}
	@Autowired
	private WeatherDataImportService weatherDataImportService;

	@Scheduled(cron = "${cron.expression:0 22 * * * ?}")
	public void importWeatherDataJob() {
		weatherDataImportService.importWeatherData();
	}


}
