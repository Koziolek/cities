package pl.koziolekweb.cities.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {
	@Id
	@Column(name = "id", nullable = false)
	private Long id;
	@Column(columnDefinition = "TEXT")
	private String name;
	@Column(columnDefinition = "TEXT")
	private String photo;

}
