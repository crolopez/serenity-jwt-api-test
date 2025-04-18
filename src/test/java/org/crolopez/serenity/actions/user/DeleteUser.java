package org.crolopez.serenity.actions.user;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Delete;

public class DeleteUser implements Task {

    private final long userId;

    public DeleteUser(long userId) {
        this.userId = userId;
    }

    public static Performable byId(long userId) {
        return Tasks.instrumented(DeleteUser.class, userId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("auth_token");
        actor.attemptsTo(
            Delete.from("/users/" + userId)
                .with(request -> request.header("Authorization", "Bearer " + token))
        );
        System.out.println(actor.getName() + " performs DeleteUser task for ID " + userId);
    }
} 