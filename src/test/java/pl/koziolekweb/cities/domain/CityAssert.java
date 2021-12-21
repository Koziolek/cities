package pl.koziolekweb.cities.domain;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;

public class CityAssert extends AbstractObjectAssert<CityAssert, City> {

	private CityAssert(City actual) {
		super(actual, CityAssert.class);
	}

	public static CityAssert assertThat(City actual) {
		return new CityAssert(actual);
	}

	public CityAssert hasName(String name) {
		Assertions.assertThat(actual.getName()).isEqualTo(name);
		return this;
	}

	public CityAssert hasPhoto(String photo) {
		Assertions.assertThat(actual.getPhoto()).isEqualTo(photo);
		return this;
	}

	public CityAssert hasId(Long id) {
		Assertions.assertThat(actual.getId()).isEqualTo(id);
		return this;
	}
}
