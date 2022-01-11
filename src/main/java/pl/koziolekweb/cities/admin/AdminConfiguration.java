package pl.koziolekweb.cities.admin;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class AdminConfiguration {

	@Bean
	AdminQuery adminQuery(AdminState adminState) {
		return new AdminQuery(adminState);
	}

	@Bean
	AdminMutation adminMutation(AdminState adminState) {
		return new AdminMutation(adminState);
	}

	@Bean
	public Algorithm jwtAlgorithm() {
		return Algorithm.HMAC256("secret"); // FIXME: Move to env or something like this. here just for simplification
	}

	@Bean
	public JWTVerifier verifier(Algorithm algorithm) {
		return JWT
				.require(algorithm)
				.withIssuer("graphql-api") // FIXME: Move to env or something like this. here just for simplification
				.build();
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}


	@Bean
	public AuthenticationProvider authenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}
}
