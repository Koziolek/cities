package pl.koziolekweb.cities.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners({CityNameNormalizator.class})
public class City {
	@Id
	@Column(name = "id", nullable = false)
	private Long id;
	@Column(columnDefinition = "TEXT")
	private String name;
	@Column(columnDefinition = "TEXT")
	private String photo;
	@Column(columnDefinition = "TEXT")
	private String normalizedName;

	public City(Long id, String name, String photo) {
		this.id = id;
		this.name = name;
		this.photo = photo;
	}

}
