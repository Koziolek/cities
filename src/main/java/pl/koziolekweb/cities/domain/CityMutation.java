package pl.koziolekweb.cities.domain;

import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CityMutation implements GraphQLMutationResolver {

	private final CityRepository cityRepository;

	/*
		simplification – we need all data to update otherwise we will null some data.
	 */
	public final City updateCity(City city) {
		return cityRepository.findById(city.getId())
				.map(byId -> {
					byId.setPhoto(city.getPhoto());
					byId.setName(city.getName());
					return byId;
				}).map(cityRepository::save)
				.orElse(city);
	}
}
