package pl.koziolekweb.cities.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
class CityPage {
	private final List<City> cities;
	private final int totalPages;
	private final int currentPage;
}
