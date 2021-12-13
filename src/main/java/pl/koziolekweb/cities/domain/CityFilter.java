package pl.koziolekweb.cities.domain;

import lombok.Data;

@Data
class CityFilter {
	private String name;

	public City toCity(){
		return new CityBuilder()
				.name(this.name)
				.build();
	}

	private static final class CityBuilder {
		private Long id;
		private String name;
		private String photo;

		public CityBuilder() {
		}

		public CityBuilder id(Long val) {
			id = val;
			return this;
		}

		public CityBuilder name(String val) {
			name = val;
			return this;
		}

		public CityBuilder photo(String val) {
			photo = val;
			return this;
		}

		public City build() {
			var city =  new City();
			city.setId(this.id);
			city.setName(this.name);
			city.setPhoto(this.photo);
			return city;
		}
	}
}
