package pl.koziolekweb.cities.admin;

import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AdminMutation implements GraphQLMutationResolver {
	private final AdminState adminState;

	public String setRole(String role) {
		adminState.setCurrentRole(role);
		return role;
	}
}
