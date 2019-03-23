package org.lrp.steps;

import cucumber.api.java8.En;
import gherkin.deps.com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.lrp.helpers.StepPlaceholdersHelper;
import org.lrp.world.LastRequestData;
import org.lrp.world.LastResponseData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class LastResponseSteps implements En {

    private LastRequestData lastRequestData;
    private LastResponseData lastResponseData;

    public LastResponseSteps(LastRequestData lastRequestData, LastResponseData lastResponseData) {
        this.lastRequestData = lastRequestData;
        this.lastResponseData = lastResponseData;

        Gson gson = new Gson();

        Then("^last response should be returned with code (\\d+)$", (Integer expectedCode) -> {
            assertThat(lastResponseData.getResponse().statusCode(), equalTo(expectedCode));
        });

        And("^last response should contain \"([^\"]*)\" entity similar to requested for (?:creation|update)$", (String className) -> {
            Class<?> expectedClass = Class.forName(className);

            Object responseEntity = gson.fromJson(lastResponseData.getResponse()
                                                                  .body()
                                                                  .asString(), expectedClass);

            assertThat(responseEntity, instanceOf(expectedClass));
            assertThat(responseEntity, equalTo(lastRequestData.getRequestEntity()));
        });

        And("^last response should contain \"([^\"]*)\" entity with following data$", (String className, String body) -> {
            StepPlaceholdersHelper stepPlaceholdersHelper = new StepPlaceholdersHelper();
            body = stepPlaceholdersHelper.replaceLastCreatedUserIdInStep(body, lastRequestData);

            Class<?> expectedClass = Class.forName(className);

            Object expectedResponseEntity = gson.fromJson(body, expectedClass);

            Object actualResponseEntity = gson.fromJson(lastResponseData.getResponse()
                                                                  .body()
                                                                  .asString(), expectedClass);

            assertThat(actualResponseEntity, instanceOf(expectedClass));
            assertThat(actualResponseEntity, equalTo(expectedResponseEntity));
        });

        And("^last response body should be empty$", () -> {
            assertTrue(StringUtils.isBlank(lastResponseData.getResponse().body().asString()));
        });
    }
}
