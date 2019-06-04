package edu.msstate.nsparc.wings.integration.xray.enums;

import java.util.stream.Stream;

public enum TestType {
    REGRESSION("WINGS-10495"),
    SMOKE("WINGS-10408");

    String testPlanId;

    TestType(String testPlanId) {
        this.testPlanId = testPlanId;
    }

    public String getTestPlanId() {
        return testPlanId;
    }

    public static String getTestTypeByTestPlan(String testPlanId) {
        return Stream.of(TestType.values()).filter(x -> x.testPlanId.equals(testPlanId)).findAny().orElseThrow(
                () -> new RuntimeException("Cannot define object type for requested value: ".concat(testPlanId))).name();
    }
}
