package edu.msstate.nsparc.wings.integration.functions;

import aqa.properties.PropertiesResourceManager;
import framework.CommonFunctions;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BambooHttpFunctions {
    private static PropertiesResourceManager props = new PropertiesResourceManager("bamboo.properties");

    private static RequestSpecification authenticate() {
        return RestAssured.given().auth().preemptive().basic(props.getProperty("bamboo.username"), props.getProperty("bamboo.password"));
    }

    private static String getLatestBuild(String projectBuildKey) {
        String regex = String.format("(%1$s-)(\\d*)", projectBuildKey);
        String httpsRequest = props.getProperty("bamboo.url").concat(String.format(props.getProperty("bamboo.request"), projectBuildKey));
        Response response = authenticate().when().get(httpsRequest);

        return CommonFunctions.regexGetMatch(response.body().asString(), regex);
    }

    public static String getLatestAppBuild() {
        return getLatestBuild(props.getProperty("bamboo.appBuildKey"));
    }
}