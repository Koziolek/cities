package pl.koziolekweb.cities.admin;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.koziolekweb.cities.domain.CityRepository;
import pl.koziolekweb.cities.http.JWTUserDetails;
import pl.koziolekweb.cities.http.SecurityProperties;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class UserServiceSimpleImpl implements UserService {

	private final SecurityProperties properties;
	private final Algorithm algorithm;
	private final JWTVerifier verifier;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository
				.findByName(username)
				.map(user -> getUserDetails(user, getToken(user)))
				.orElseThrow(() -> new UsernameNotFoundException("Username or password didn''t match"));
	}

	@Override
	public JWTUserDetails loadUserByToken(String token) {
		return null;
	}

	@Transactional
	public String getToken(User user) {
		Instant now = Instant.now();
		Instant expiry = Instant.now().plus(properties.getTokenExpiration());
		return JWT
				.create()
				.withIssuer(properties.getTokenIssuer())
				.withIssuedAt(Date.from(now))
				.withExpiresAt(Date.from(expiry))
				.withSubject(user.getName())
				.sign(algorithm);
	}


	private JWTUserDetails getUserDetails(User user, String token) {
		return JWTUserDetails
				.builder()
				.username(user.getName())
				.password(user.getPassword())
				.authorities(user.roles().stream()
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList()))
				.token(token)
				.build();
	}
}
