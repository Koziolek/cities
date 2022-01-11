package pl.koziolekweb.cities.http;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.koziolekweb.cities.admin.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.function.Predicate.not;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final Pattern BEARER_PATTERN = Pattern.compile("^Bearer (.+?)$");
	private final UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		getToken(request)
				.map(userService::loadUserByToken)
				.map(userDetails -> JWTPreAuthenticationToken
						.builder()
						.principal(userDetails)
						.details(new WebAuthenticationDetailsSource().buildDetails(request))
						.build())
				.ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
		filterChain.doFilter(request, response);

	}

	private Optional<String> getToken(HttpServletRequest request) {
		return Optional
				.ofNullable(request.getHeader(AUTHORIZATION_HEADER))
				.filter(not(String::isEmpty))
				.map(BEARER_PATTERN::matcher)
				.filter(Matcher::find)
				.map(matcher -> matcher.group(1));
	}
}

