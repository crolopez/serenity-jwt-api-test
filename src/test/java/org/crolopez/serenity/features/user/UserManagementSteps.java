package org.crolopez.serenity.features.user;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import net.serenitybdd.rest.SerenityRest;

import org.crolopez.serenity.actions.authentication.Login; 
import org.crolopez.serenity.actions.user.*;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.*;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class UserManagementSteps {

    private final String baseUrl = "http://localhost:8080/api/v1";
    private Actor admin = Actor.named("Admin");

    @Before
    public void setTheStage() {
        admin.can(CallAnApi.at(baseUrl));
    }

    // ---------- GIVEN STEPS ----------

    @Given("the user {string} is registered and logged in with password {string}")
    public void the_user_is_registered_and_logged_in(String username, String password) {
        System.out.println("Given user logged in: " + username);
        admin.attemptsTo(Login.withCredentials(username, password));
        String token = SerenityRest.lastResponse().jsonPath().getString("data");
        admin.remember("auth_token", token);
    }

    @Given("a user exists with ID {long}")
    public void a_user_exists_with_id(Long userId) {
        admin.remember("target_user_id", userId);
        System.out.println("Given user exists with ID: " + userId);
    }

    @Given("the user wants to create a user with username {string}, email {string}, password {string}, and role {string}")
    public void the_user_wants_to_create_user(String username, String email, String password, String role) {
        admin.remember("create_username", username);
        admin.remember("create_email", email);
        admin.remember("create_password", password);
        admin.remember("create_role", role);
        System.out.println("Given wants to create user: " + username);
    }

    @Given("the user wants to update user {long} with username {string}, email {string}, and role {string}")
    public void the_user_wants_to_update_user(Long userId, String username, String email, String role) {
        admin.remember("update_user_id", userId);
        admin.remember("update_username", username);
        admin.remember("update_email", email);
        admin.remember("update_role", role);
        System.out.println("Given wants to update user ID: " + userId);
    }

    @Given("the user is not authenticated")
    public void the_user_is_not_authenticated() {
        admin.forget("auth_token"); // Asegurarse de que no hay token residual
        System.out.println("Given user is not authenticated");
    }

    // ---------- WHEN STEPS ----------

    @When("the user attempts to get all users")
    public void the_user_attempts_to_get_all_users() {
        System.out.println("When getting all users");
        admin.attemptsTo(GetUsers.all());
    }

    @When("the user attempts to get the user by ID {long}")
    public void the_user_attempts_to_get_user_by_id(Long userId) {
        System.out.println("When getting user by ID: " + userId);
        admin.attemptsTo(GetUser.byId(userId));
    }

    @When("the user attempts to create the user")
    public void the_user_attempts_to_create_the_user() {
        String username = admin.recall("create_username");
        String email = admin.recall("create_email");
        String password = admin.recall("create_password");
        String role = admin.recall("create_role");
        System.out.println("When creating user: " + username);
        admin.attemptsTo(CreateUser.withDetails(username, email, password, role));
    }

    @When("the user attempts to update the user")
    public void the_user_attempts_to_update_the_user() {
        Long userId = admin.recall("update_user_id");
        String username = admin.recall("update_username");
        String email = admin.recall("update_email");
        String role = admin.recall("update_role");
        System.out.println("When updating user ID: " + userId);
        admin.attemptsTo(UpdateUser.byId(userId).withDetails(username, email, role));
    }

    @When("the user attempts to delete the user by ID {long}")
    public void the_user_attempts_to_delete_user(Long userId) {
        System.out.println("When deleting user ID: " + userId);
        admin.attemptsTo(DeleteUser.byId(userId));
    }

    // ---------- THEN STEPS ----------

    @Then("the request should be successful with status code {int}")
    public void the_request_should_be_successful(Integer statusCode) {
        System.out.println("Then request successful with status: " + statusCode);
        admin.should(seeThatResponse("The status code should be " + statusCode,
                response -> response.statusCode(statusCode)));
    }

    @Then("the response should contain a list of users")
    public void the_response_should_contain_list_of_users() {
        System.out.println("And response contains list of users");
        admin.should(seeThatResponse(response -> response.body("data", not(empty()))));
    }

    @Then("the response should contain the details of user {long}")
    public void the_response_should_contain_user_details(Long userId) {
        System.out.println("And response contains details for user ID: " + userId);
        admin.should(
            seeThatResponse(response -> response
                .body("data.id", equalTo(userId.intValue()))
            )
        );
    }

    @Then("the request should fail with status code {int}")
    public void the_request_should_fail_with_status_code(Integer statusCode) {
        System.out.println("Then request failed with status: " + statusCode);
        admin.should(seeThatResponse("The status code should be " + statusCode,
                response -> response.statusCode(statusCode)));
    }

    @Then("the response message should indicate that the user with ID {long} was not found")
    public void the_response_message_indicates_user_not_found(Long userId) {
        System.out.println("And response errorMessage indicates user not found: " + userId);
        String expectedMessage = String.format("User with ID %d was not found", userId);
        admin.should(seeThatResponse(response -> response.body("errorMessage", equalTo(expectedMessage))));
    }

    @Then("the response should contain the created user details excluding the password")
    public void the_response_should_contain_created_user_details() {
        String username = admin.recall("create_username");
        String email = admin.recall("create_email");
        System.out.println("And response contains created user details: " + username);
        admin.should(
             seeThatResponse(response -> response
                .body("data.username", equalTo(username))
                .body("data.email", equalTo(email))
                .body("data", not(hasKey("password"))))
        );
    }

    @Then("the response should contain the updated user details for {long}")
    public void the_response_should_contain_updated_user_details(Long userId) {
        String username = admin.recall("update_username");
        String email = admin.recall("update_email");
        System.out.println("And response contains updated user details for ID: " + userId);
        admin.should(
            seeThatResponse(response -> response
                .body("data.id", equalTo(userId.intValue()))
                .body("data.username", equalTo(username))
                .body("data.email", equalTo(email)))
        );
    }

    @Then("the request should fail with status code {int} or {int}")
    public void the_request_should_fail_with_status_code_or(Integer code1, Integer code2) {
        System.out.println("Then request failed with status " + code1 + " or " + code2);
        admin.should(seeThatResponse("The status code should be " + code1 + " or " + code2,
            response -> response.statusCode(isOneOf(code1, code2))
        ));
    }
} 