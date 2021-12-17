package pl.koziolekweb.cities;

import graphql.kickstart.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.koziolekweb.cities.admin.AdminMutation;
import pl.koziolekweb.cities.admin.AdminQuery;
import pl.koziolekweb.cities.domain.CityMutation;
import pl.koziolekweb.cities.domain.CityQuery;

@Configuration
class GQLConfiguration {

	@Bean
	GraphQLSchema schema(AdminQuery adminQuery, AdminMutation adminMutation, CityQuery cityQuery, CityMutation cityMutation) {
		return SchemaParser.newParser().file("cities.graphqls")
				.resolvers(adminQuery, adminMutation, cityQuery, cityMutation)
				.build()
				.makeExecutableSchema();
	}
}
