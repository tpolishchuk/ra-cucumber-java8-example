package org.lrp.steps;

import cucumber.api.java8.En;
import demo_app.domain.User;
import gherkin.deps.com.google.gson.Gson;
import org.lrp.helpers.RandomEntitiesHelper;
import org.lrp.helpers.requests.UserAPIRequestsHelper;
import org.lrp.world.LastRequestData;
import org.lrp.world.LastResponseData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UsersAPISteps implements En {

    private LastRequestData lastRequestData;
    private LastResponseData lastResponseData;

    public UsersAPISteps(LastRequestData lastRequestData, LastResponseData lastResponseData) {
        this.lastRequestData = lastRequestData;
        this.lastResponseData = lastResponseData;

        UserAPIRequestsHelper userAPIRequestsHelper = new UserAPIRequestsHelper();
        RandomEntitiesHelper randomEntitiesHelper = new RandomEntitiesHelper();
        Gson gson = new Gson();

        When("^user is created with following request body$", (String body) -> {
            body = randomEntitiesHelper.generateRandomEntitiesForStep(body);

            lastRequestData.setRequestEntity(gson.fromJson(body, User.class));
            lastResponseData.setResponse(userAPIRequestsHelper.createUser(body));

            if (lastResponseData.getResponse().statusCode() == 200) {
                User user = gson.fromJson(lastResponseData.getResponse()
                                                          .body()
                                                          .asString(), User.class);
                lastRequestData.setLastCreatedUser(user);
            }
        });

        When("^the last created user is updated with following request body$", (String body) -> {
            body = randomEntitiesHelper.generateRandomEntitiesForStep(body);

            User user = lastRequestData.getLastCreatedUser();

            lastRequestData.setRequestEntity(gson.fromJson(body, User.class));
            lastResponseData.setResponse(userAPIRequestsHelper.updateUser(user.getId(), body));
        });

        And("^user is created with the same request as before$", () -> {
            User user = lastRequestData.getLastCreatedUser();
            user.setId(null);

            String requestBody = gson.toJson(user);

            lastResponseData.setResponse(userAPIRequestsHelper.createUser(requestBody));
        });

        And("^the last created user is got by id$", () -> {
            User user = lastRequestData.getLastCreatedUser();

            lastResponseData.setResponse(userAPIRequestsHelper.getUser(user.getId()));
        });

        When("^the last created user is deleted by id$", () -> {
            User user = lastRequestData.getLastCreatedUser();

            lastResponseData.setResponse(userAPIRequestsHelper.deleteUser(user.getId()));
        });

        When("^a nonexistent user is got by id$", () -> {
            Integer id = 999999;

            lastResponseData.setResponse(userAPIRequestsHelper.getUser(id));
        });

        When("^a nonexistent user is deleted by id$", () -> {
            Integer id = 999999;

            lastResponseData.setResponse(userAPIRequestsHelper.deleteUser(id));
        });

        And("^last response should contain a user with amended nickname as requested$", () -> {
            User userFromRequest = (User) lastRequestData.getRequestEntity();
            User userFromResponse = gson.fromJson(lastResponseData.getResponse()
                                                                  .body()
                                                                  .asString(), User.class);

            assertThat(userFromResponse.getNickname(), equalTo(userFromRequest.getNickname()));
        });

        And("^last response should contain a user with active flag equal to (true|false)$", (String expectedValue) -> {
            User userFromResponse = gson.fromJson(lastResponseData.getResponse()
                                                                  .body()
                                                                  .asString(), User.class);

            assertThat(userFromResponse.getActive(), equalTo(Boolean.parseBoolean(expectedValue)));
        });

        And("^last response should contain a user with not changed id$", () -> {
            User userFromResponse = gson.fromJson(lastResponseData.getResponse()
                                                                  .body()
                                                                  .asString(), User.class);

            assertThat(userFromResponse.getId(),
                       equalTo(lastRequestData.getLastCreatedUser().getId()));
        });

        And("^last response should contain a user with not changed nickname$", () -> {
            User userFromResponse = gson.fromJson(lastResponseData.getResponse()
                                                                  .body()
                                                                  .asString(), User.class);

            assertThat(userFromResponse.getNickname(),
                       equalTo(lastRequestData.getLastCreatedUser().getNickname()));
        });
    }
}
