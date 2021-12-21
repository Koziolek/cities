package pl.koziolekweb.cities.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CityConfiguration {

	@Bean
	CityQuery cityQuery(CityRepository citiRepository) {
		return new CityQuery(citiRepository);
	}

	@Bean
	CityMutation cityMutation(CityRepository citiRepository) {
		return new CityMutation(citiRepository);
	}

}
