package pl.koziolekweb.cities.domain;

import graphql.kickstart.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.koziolekweb.cities.domain.CityQuery;
import pl.koziolekweb.cities.domain.CityRepository;
import pl.koziolekweb.cities.domain.CityMutation;

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
