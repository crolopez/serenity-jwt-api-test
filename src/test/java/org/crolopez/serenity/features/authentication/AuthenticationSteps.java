package org.crolopez.serenity.features.authentication;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import net.serenitybdd.rest.SerenityRest;

import org.crolopez.serenity.actions.authentication.Login;
import org.crolopez.serenity.actions.authentication.Register;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.*;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class AuthenticationSteps {

    private final String baseUrl = "http://localhost:8080/api/v1";
    private Actor user = Actor.named("User");

    @Before
    public void setTheStage() {
        user.can(CallAnApi.at(baseUrl));
    }

    @Given("a registered user with username {string} and password {string}")
    public void a_registered_user_with_username_and_password(String username, String password) {
        user.remember("username", username);
        user.remember("password", password);
        System.out.println("Given user exists: " + username);
    }

    @Given("a registered user exists")
    public void a_registered_user_exists() {
        System.out.println("Given a registered user exists");
    }

    @Given("the user wants to register with username {string}, email {string}, and password {string}")
    public void the_user_wants_to_register(String username, String email, String password) {
        user.remember("register_username", username);
        user.remember("register_email", email);
        user.remember("register_password", password);
        System.out.println("Given user wants to register: " + username);
    }

    @Given("a user is already registered with username {string}")
    public void a_user_is_already_registered_with_username(String username) {
        System.out.println("Given user already registered with username: " + username);
    }

    @Given("a user is already registered with email {string}")
    public void a_user_is_already_registered_with_email(String email) {
        System.out.println("Given user already registered with email: " + email);
    }

    @When("the user attempts to log in with username {string} and password {string}")
    public void the_user_attempts_to_log_in(String username, String password) {
        System.out.println("When user attempts to log in: " + username);
        user.attemptsTo(Login.withCredentials(username, password));
    }

    @When("the user attempts to register")
    public void the_user_attempts_to_register() {
        String username = user.recall("register_username");
        String email = user.recall("register_email");
        String password = user.recall("register_password");
        System.out.println("When user attempts to register: " + username);
        user.attemptsTo(Register.newUser(username, email, password));
    }

    @Then("the login attempt should be successful")
    public void the_login_attempt_should_be_successful() {
        System.out.println("Then login should be successful");
        user.should(seeThatResponse("The status code should be 200",
                response -> response.statusCode(200)));
    }

    @Then("a valid JWT token should be returned")
    public void a_valid_jwt_token_should_be_returned() {
        System.out.println("And a valid JWT token returned");
        user.should(seeThatResponse(response -> response.body("data", containsString("."))));
        String token = SerenityRest.lastResponse().jsonPath().getString("data");
        user.remember("auth_token", token);
    }

    @Then("the login attempt should fail with status code {int}")
    public void the_login_attempt_should_fail_with_status_code(Integer statusCode) {
        System.out.println("Then login should fail with status: " + statusCode);
        user.should(seeThatResponse("The status code should be " + statusCode,
                response -> response.statusCode(statusCode)));
    }

    @Then("the response message should indicate {string}")
    public void the_response_message_should_indicate(String message) {
        System.out.println("And response errorMessage contains: " + message);
        user.should(seeThatResponse(response -> response.body("errorMessage", equalTo(message))));
    }

    @Then("the registration should be successful with status code {int}")
    public void the_registration_should_be_successful(Integer statusCode) {
        System.out.println("Then registration is successful with status: " + statusCode);
        user.should(seeThatResponse("The status code should be " + statusCode,
                response -> response.statusCode(statusCode)));
    }

    @Then("the response should contain the registered user details excluding the password")
    public void the_response_should_contain_registered_user_details() {
        String username = user.recall("register_username");
        String email = user.recall("register_email");
        System.out.println("And response contains user details for: " + username);
        user.should(
            seeThatResponse(response -> response
                .body("data.username", equalTo(username))
                .body("data.email", equalTo(email))
                .body("data", not(hasKey("password"))))
        );
    }

    @Then("the registration should fail with status code {int}")
    public void the_registration_should_fail(Integer statusCode) {
        System.out.println("Then registration fails with status: " + statusCode);
        user.should(seeThatResponse("The status code should be " + statusCode,
                response -> response.statusCode(statusCode)));
    }

    @Then("the response message should indicate that the username {string} is already taken")
    public void the_response_message_indicates_username_taken(String username) {
        System.out.println("And response message indicates username taken: " + username);
        String expectedMessage = String.format("Username '%s' is already taken", username);
        user.should(seeThatResponse(response -> response.body("errorMessage", equalTo(expectedMessage))));
    }

    @Then("the response message should indicate that the email {string} is already registered")
    public void the_response_message_indicates_email_registered(String email) {
        System.out.println("And response message indicates email registered: " + email);
        String expectedMessage = String.format("Email '%s' is already registered", email);
        user.should(seeThatResponse(response -> response.body("errorMessage", equalTo(expectedMessage))));
    }
} 