package org.crolopez.serenity.actions.user;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class GetUsers implements Task {

    public static Performable all() {
        return Tasks.instrumented(GetUsers.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("auth_token");
        actor.attemptsTo(
            Get.resource("/users")
                .with(request -> request.header("Authorization", "Bearer " + token))
        );
        System.out.println(actor.getName() + " performs GetUsers task");
    }
} 