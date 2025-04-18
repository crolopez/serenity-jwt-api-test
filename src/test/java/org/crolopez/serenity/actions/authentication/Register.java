package org.crolopez.serenity.actions.authentication;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

public class Register implements Task {

    private final String username;
    private final String email;
    private final String password;

    public Register(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static Performable newUser(String username, String email, String password) {
        return Tasks.instrumented(Register.class, username, email, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Post.to("/auth/register")
                .with(request -> request
                    .header("Content-Type", "application/json")
                    .body(Map.of("username", username, "email", email, "password", password)))
        );
        System.out.println(actor.getName() + " performs Register task for user " + username);
    }
} 