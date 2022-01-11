package pl.koziolekweb.cities.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
	@Id
	@Column(name = "user_id", insertable = false, updatable = false)
	@EqualsAndHashCode.Include
	private Long userId;
	@EqualsAndHashCode.Include
	private String name;
	@Setter
	@EqualsAndHashCode.Include
	private String password;
	@ElementCollection
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "name")
	private Set<String> roles;

	public User withRole(String role) {
		if (this.roles == null) {
			this.roles = Set.of(role);
		} else {
			this.roles.add(role);
		}
		return this;
	}

	public User withoutRole(String role) {
		if (this.roles != null) {
			this.roles.remove(role);
		}
		return this;
	}

	public Set<String> roles() {
		return Set.copyOf(roles);
	}
}