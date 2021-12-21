package pl.koziolekweb.cities.domain;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.koziolekweb.cities.domain.CityAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityQueryTest {

	private static final String TOKYO_IMG = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/500px-Skyscrapers_of_Shinjuku_2009_January.jpg";
	@Autowired
	private GraphQLTestTemplate graphQLTestTemplate;

	@Test
	@DisplayName("Should return single city – Tokyo, that match to DB entry")
	void cityByName() throws IOException {
		GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("graphql/get-city.graphql");
		assertThat(graphQLResponse)
				.matches(r -> r.isOk());
		assertThat(graphQLResponse.get("data.cityByName", City.class))
				.hasName("Tokyo")
				.hasPhoto(TOKYO_IMG)
				.hasId(1L);
	}

	@Test
	@DisplayName("Should return page of cities with size 5")
	void cities() throws IOException {
		GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("graphql/get-cities.graphql");
		assertThat(graphQLResponse)
				.matches(r -> r.isOk());

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