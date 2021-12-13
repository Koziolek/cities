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
	CityQuery query(CityRepository citiRepository) {
		return new CityQuery(citiRepository);
	}

	@Bean
	CityMutation mutation(CityRepository citiRepository) {
		return new CityMutation(citiRepository);
	}

	@Bean
	GraphQLSchema schema(CityQuery cityQuery, CityMutation cityMutation) {
		return SchemaParser.newParser().file("schema.graphqls").resolvers(cityQuery, cityMutation).build().makeExecutableSchema();
	}
}
