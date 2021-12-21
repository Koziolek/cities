package pl.koziolekweb.cities.domain;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@AllArgsConstructor
public class CityQuery implements GraphQLQueryResolver {

	private final CityRepository cityRepository;

	public final CityPage cities(int pageNumber, int pageSize) {

		Page<City> all = cityRepository.findAll(PageRequest.of(pageNumber, pageSize));

		return CityPage.builder()
				.cities(all.toList())
				.totalPages(all.getTotalPages())
				.currentPage(all.getNumber())
				.build();
	}

	public final Optional<City> cityByName(String name) {
		return cityRepository.findByName(name);
	}

}
