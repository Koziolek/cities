package pl.koziolekweb.cities.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
class CityPage {

	private final List<City> cities;
	private final int totalPages;
	private final int currentPage;
}
