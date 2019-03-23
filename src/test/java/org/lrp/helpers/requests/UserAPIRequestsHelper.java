package org.lrp.helpers.requests;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.lrp.helpers.TestConfigReader;

public class UserAPIRequestsHelper extends AbstractRequestsHelper {

    private String userCrudUrl;

    public UserAPIRequestsHelper() {
        userCrudUrl = TestConfigReader.getInstance().getProperty("endpoints.userCrud");
    }

    public ExtractableResponse<Response> createUser(String body) {
        return sendRequest(Method.POST, userCrudUrl, body, ContentType.JSON);
    }

    public ExtractableResponse<Response> getUsers() {
        return sendRequest(Method.GET, userCrudUrl);
    }

    public ExtractableResponse<Response> getUser(Integer id) {
        String relativeUrl = userCrudUrl + "/" + id;
        return sendRequest(Method.GET, relativeUrl);
    }

    public ExtractableResponse<Response> updateUser(Integer id, String body) {
        String relativeUrl = userCrudUrl + "/" + id;
        return sendRequest(Method.PUT, relativeUrl, body, ContentType.JSON);
    }

    public ExtractableResponse<Response> deleteUser(Integer id) {
        String relativeUrl = userCrudUrl + "/" + id;
        return sendRequest(Method.DELETE, relativeUrl);
    }
}
