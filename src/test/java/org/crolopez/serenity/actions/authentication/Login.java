package org.crolopez.serenity.actions.authentication;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

public class Login implements Task {

    private final String username;
    private final String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Performable withCredentials(String username, String password) {
        return Tasks.instrumented(Login.class, username, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Post.to("/auth/login")
                .with(request -> request
                    .header("Content-Type", "application/json")
                    .body(Map.of("username", username, "password", password)))
        );
        System.out.println(actor.getName() + " performs Login task for user " + username);
    }
} 