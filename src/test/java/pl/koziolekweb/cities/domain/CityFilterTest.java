package pl.koziolekweb.cities.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static pl.koziolekweb.cities.domain.CityAssert.assertThat;

class CityFilterTest {

	private static final String TOKYO = "Tokyo";

	@Test
	@DisplayName("Should create City from filter params")
	void buildCity() {
		CityFilter cityFilter = new CityFilter();
		cityFilter.setName(TOKYO);

		City city = cityFilter.toCity();

		assertThat(city)
				.isNotNull()
				.hasName(TOKYO)
				.hasPhoto(null);
	}
}