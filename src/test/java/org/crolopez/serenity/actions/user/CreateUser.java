package org.crolopez.serenity.actions.user;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;
import java.util.Map;

public class CreateUser implements Task {

    private final String username;
    private final String email;
    private final String password;
    private final String role;

    public CreateUser(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static Performable withDetails(String username, String email, String password, String role) {
        return Tasks.instrumented(CreateUser.class, username, email, password, role);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("auth_token");
        actor.attemptsTo(
            Post.to("/users")
                .with(request -> request
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .body(Map.of(
                        "username", username,
                        "email", email,
                        "password", password, 
                        "role", role
                    )))
        );
        System.out.println(actor.getName() + " performs CreateUser task for " + username);
    }
} 