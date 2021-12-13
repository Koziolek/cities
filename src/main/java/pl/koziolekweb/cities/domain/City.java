package pl.koziolekweb.cities.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class City {
	@Id
	@Column(name = "id", nullable = false)
	private Long id;
	@Column(columnDefinition="TEXT")
	private String name;
	@Column(columnDefinition="TEXT")
	private String photo;

}
