package pl.koziolekweb.cities.domain;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.koziolekweb.cities.domain.CityAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CityQueryTest {

	private static final String TOKYO_IMG = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/500px-Skyscrapers_of_Shinjuku_2009_January.jpg";

	@Autowired
	private GraphQLTestTemplate graphQLTestTemplate;

	@Test
	@DisplayName("Should return single city – Tokyo, that match to DB entry")
	void cityByName() throws IOException {
		GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("graphql/get-city.graphql");
		System.out.println(graphQLResponse.getRawResponse());
		assertThat(graphQLResponse)
				.matches(GraphQLResponse::isOk);
		City tokyo = new City.CityBuilder()
				.name("Tokyo")
				.photo(TOKYO_IMG)
				.id(1L)
				.build();

		CityPageAssert.assertThat(graphQLResponse.get("data.cityByName", CityPage.class))
				.isCurrentPage(0)
				.hasTotalPages(1)
				.hasSize(1)
				.contains(tokyo);
	}

	@Test
	@DisplayName("Should return page of cities with size 5")
	void cities() throws IOException {
		GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("graphql/get-cities.graphql");
		assertThat(graphQLResponse)
				.matches(GraphQLResponse::isOk);

		City tokyo = new City.CityBuilder()
				.name("Tokyo")
				.photo(TOKYO_IMG)
				.id(1L)
				.build();

		CityPageAssert.assertThat(graphQLResponse.get("data.cities", CityPage.class))
				.isCurrentPage(0)
				.hasTotalPages(3)
				.hasSize(5)
				.contains(tokyo);
	}
}