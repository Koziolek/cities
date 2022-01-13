package pl.koziolekweb.cities.domain;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

class CityNameNormalizator {

	@PrePersist
	@PreUpdate
	void normalize(City city){
		city.setNormalizedName(StringUtils.stripAccents(city.getName()));
	}
}
