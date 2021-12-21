package pl.koziolekweb.cities.domain;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;

public class CityPageAssert extends AbstractObjectAssert<CityPageAssert, CityPage> {

	private CityPageAssert(CityPage actual) {
		super(actual, CityPageAssert.class);
	}

	public static CityPageAssert assertThat(CityPage actual) {
		return new CityPageAssert(actual);
	}

	public CityPageAssert isCurrentPage(int currentPage) {
		Assertions.assertThat(actual.getCurrentPage()).isEqualTo(currentPage);
		return this;
	}

	public CityPageAssert hasTotalPages(int totalPages) {
		Assertions.assertThat(actual.getTotalPages()).isEqualTo(totalPages);
		return this;
	}

	public CityPageAssert contains(City... values) {
		Assertions.assertThat(actual.getCities()).contains(values);
		return this;
	}

	public CityPageAssert hasSize(int expected) {
		Assertions.assertThat(actual.getCities()).hasSize(expected);
		return this;
	}
}
