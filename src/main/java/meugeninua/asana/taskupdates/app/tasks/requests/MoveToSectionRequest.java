package meugeninua.asana.taskupdates.app.tasks.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import meugeninua.asana.taskupdates.app.models.AddProjectBody;
import meugeninua.asana.taskupdates.app.tasks.TaskInfo;
import meugeninua.asana.taskupdates.app.tasks.TaskUpdater;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Consumer;

public class MoveToSectionRequest implements Consumer<TaskInfo> {

    private static final String URL = "https://app.asana.com/api/1.0/tasks/%s/addProject";

    private final HttpClient httpClient;
    private final String token;
    private final String sectionId;

    public MoveToSectionRequest(HttpClient httpClient, String token, String sectionId) {
        this.httpClient = httpClient;
        this.token = token;
        this.sectionId = sectionId;
    }

    @Override
    public void accept(TaskInfo taskInfo) {
        try {
            var body = new AddProjectBody(taskInfo.getProjectId(), sectionId);
            var bodyPublisher = HttpRequest.BodyPublishers.ofString(
                new ObjectMapper().writeValueAsString(body)
            );
            var uri = new URI(String.format(URL, taskInfo.getTaskId()));
            var request = HttpRequest.newBuilder(uri)
                .header("Authorization", "Bearer " + token)
                .POST(bodyPublisher)
                .build();
            var response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
            );
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(response.body());
            }
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }
}
