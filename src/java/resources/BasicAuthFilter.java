/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author user
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class BasicAuthFilter implements ContainerRequestFilter {

    // Statyczna lista użytkowników
    private static final List<User> USERS = Arrays.asList(
            new User("user1", "password1"),
            new User("user2", "password2")
    );

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String encodedUserPassword = authHeader.replaceFirst("Basic ", "");
        String decodedUserPassword;
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedUserPassword);
            decodedUserPassword = new String(decodedBytes, "UTF-8");
        } catch (IOException e) {
            abortWithUnauthorized(requestContext);
            return;
        }

        final String[] userAndPassword = decodedUserPassword.split(":", 2);
        if (userAndPassword.length != 2) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String username = userAndPassword[0];
        String password = userAndPassword[1];

        // Sprawdzanie, czy użytkownik istnieje na liście
        boolean userExists = false;
        for (User user : USERS) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                userExists = true;
                break;
            }
        }
        if (!userExists) {
            abortWithUnauthorized(requestContext);
        }
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        // Wyślij 401, jeżeli użytkownik nie jest autoryzowany
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}