package edu.msstate.nsparc.wings.integration.xray.utils;

import aqa.jira.JiraConfiguration;
import com.a1qa.rest.RestClientResponse;
import com.atlassian.jira.rest.client.api.domain.IssueFieldId;
import edu.msstate.nsparc.xray.configuration.Configuration;
import framework.Logger;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class JiraIssueCreator extends edu.msstate.nsparc.xray.utils.JiraIssueCreator {
    private final JiraConfiguration jiraConfiguration = Configuration.getInstance().getJiraConfiguration();

    public JSONObject create(String projectId, String summary, String description, String issueTypeName,
                             String assigneeName, String affectsVersionName, String priorityName, String security, String testPlan) throws IOException {
        Logger.getInstance().info("========= Create JIRA issue ========");
        Logger.getInstance().info("Project Key: " + projectId);
        Logger.getInstance().info("Summary: " + summary);
        Logger.getInstance().info("Description: " + description);
        Logger.getInstance().info("Type: " + issueTypeName);
        Logger.getInstance().info("Assignee: " + assigneeName);
        Logger.getInstance().info("Affects Version: " + affectsVersionName);
        Logger.getInstance().info("Priority: " + priorityName);
        Logger.getInstance().info("Security: " + security);
        Logger.getInstance().info("Test Plan: " + testPlan);
        Logger.getInstance().info("====================================");

        JSONObject fieldsObject = new JSONObject();

        JSONObject issueTypeObject = new JSONObject();
        issueTypeObject.put("name", issueTypeName);
        fieldsObject.put(IssueFieldId.ISSUE_TYPE_FIELD.id, issueTypeObject);

        JSONObject securityObject = new JSONObject();
        securityObject.put("id", security);
        fieldsObject.put("security", securityObject);

        JSONArray testPlanArray = new JSONArray();
        testPlanArray.put(testPlan);
        fieldsObject.put("customfield_11639", testPlanArray);

        JSONObject priorityObject = new JSONObject();
        priorityObject.put("name", priorityName);
        fieldsObject.put(IssueFieldId.PRIORITY_FIELD.id, priorityObject);

        JSONObject assigneeObject = new JSONObject();
        assigneeObject.put("name", assigneeName);
        fieldsObject.put(IssueFieldId.ASSIGNEE_FIELD.id, assigneeObject);

        JSONObject versionObject = new JSONObject();
        versionObject.put("name", affectsVersionName);
        JSONArray versions = new JSONArray();
        versions.put(versionObject);
        fieldsObject.put(IssueFieldId.AFFECTS_VERSIONS_FIELD.id, versions);

        JSONObject projectObject = new JSONObject();
        projectObject.put("key", projectId);
        fieldsObject.put(IssueFieldId.PROJECT_FIELD.id, projectObject);

        fieldsObject.put(IssueFieldId.SUMMARY_FIELD.id, summary);
        fieldsObject.put(IssueFieldId.DESCRIPTION_FIELD.id, description);

        JSONObject issueObject = new JSONObject();
        issueObject.put("fields", fieldsObject);

        StringEntity postingString = new StringEntity(issueObject.toString());
        HttpPost httpPost = new HttpPost(this.jiraConfiguration.getUrl() + "/rest/api/latest/issue");
        httpPost.setEntity(postingString);
        RestClientResponse response = executePost(httpPost);
        return new JSONObject(response.getBody());
    }
}
