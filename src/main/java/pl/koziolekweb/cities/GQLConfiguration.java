package pl.koziolekweb.cities;

import graphql.kickstart.tools.GraphQLResolver;
import graphql.kickstart.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
class GQLConfiguration {

	@Bean
	GraphQLSchema schema(List<? extends GraphQLResolver<?>> resolvers) {
		log.info("Found {} resolvers", resolvers.size());
		return SchemaParser.newParser().file("cities.graphqls")
				.resolvers(resolvers)
				.build()
				.makeExecutableSchema();
	}
}
