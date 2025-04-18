package org.crolopez.serenity.actions.user;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Put;
import java.util.Map;

public class UpdateUser implements Task {

    private final long userId;
    private String username;
    private String email;
    private String role;

    public UpdateUser(long userId) {
        this.userId = userId;
    }

    public static UpdateUser byId(long userId) {
        return Tasks.instrumented(UpdateUser.class, userId);
    }

    public Performable withDetails(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
        return this;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("auth_token");
        actor.attemptsTo(
            Put.to("/users/" + userId)
                .with(request -> request
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .body(Map.of(
                        "username", username,
                        "email", email,
                        "role", role
                    )))
        );
        System.out.println(actor.getName() + " performs UpdateUser task for ID " + userId);
    }
} 