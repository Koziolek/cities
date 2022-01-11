package pl.koziolekweb.cities.admin;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.koziolekweb.cities.http.JWTUserDetails;

public interface UserService extends UserDetailsService {

	JWTUserDetails loadUserByToken(String token);
}
