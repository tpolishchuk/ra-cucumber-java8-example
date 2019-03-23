package org.lrp.helpers.requests;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.lrp.helpers.TestConfigReader;

import java.util.Collections;
import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class AbstractRequestsHelper {

    private static final String BASIC_URL = TestConfigReader.getInstance().getBasicUrl();

    public ExtractableResponse<Response> sendRequest(Method method,
                                                     String relativeUrl) {
        return sendRequest(method, relativeUrl, StringUtils.EMPTY,
                           null, Collections.emptyMap(), Collections.emptyMap());
    }

    public ExtractableResponse<Response> sendRequest(Method method,
                                                     String relativeUrl,
                                                     String body,
                                                     ContentType contentType) {
        return sendRequest(method, relativeUrl, body, contentType,
                           Collections.emptyMap(), Collections.emptyMap());
    }

    private ExtractableResponse<Response> sendRequest(Method method,
                                                      String relativeUrl,
                                                      String body,
                                                      ContentType contentType,
                                                      Map<String, String> headers,
                                                      Map<String, String> params) {
        RequestSpecification rs = given().body(body)
                                         .headers(headers)
                                         .params(params);

        if (contentType != null) {
            rs.contentType(contentType);
        }

        return rs.when().request(method, BASIC_URL + relativeUrl)
                 .then().extract();
    }
}
