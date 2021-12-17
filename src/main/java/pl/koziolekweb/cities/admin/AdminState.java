package pl.koziolekweb.cities.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Very simple security.
 */
@Component
@Data
class AdminState {

	private String currentRole;

}
