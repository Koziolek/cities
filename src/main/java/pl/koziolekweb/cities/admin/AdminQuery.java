package pl.koziolekweb.cities.admin;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AdminQuery implements GraphQLQueryResolver {

	private final AdminState adminState;

	public final String getRole() {
		return adminState.getCurrentRole();
	}
}
