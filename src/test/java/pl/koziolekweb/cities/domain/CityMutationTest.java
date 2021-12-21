package pl.koziolekweb.cities.domain;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CityMutationTest {

	@Autowired
	private GraphQLTestTemplate graphQLTestTemplate;

	@Test
	@DisplayName("Should change Tokyo to Kyoto")
	void updateCity() throws IOException {
		GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("graphql/update-city.graphql");
		assertThat(graphQLResponse)
				.matches(r -> r.isOk());

		CityAssert.assertThat(graphQLResponse.get("data.updateCity", City.class))
				.hasName("Kyoto")
				.hasPhoto("")
				.hasId(1L);

	}
}