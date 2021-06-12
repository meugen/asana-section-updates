package meugeninua.asana.sectionupdates.app.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import meugeninua.asana.sectionupdates.app.models.AddProjectBody;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class TaskMover {

    private final HttpClient httpClient;

    public TaskMover(HttpClient httpClient) {
        this.httpClient = Objects.requireNonNull(httpClient);
    }

    public Request makeRequest(String token, String sectionId) {
        return new TaskMoverRequest(
                httpClient,
                Objects.requireNonNull(token),
                Objects.requireNonNull(sectionId)
        );
    }

    public interface Request {

        void moveToSection(TaskInfo task);
    }
}

class TaskMoverRequest implements TaskMover.Request {

    private static final String URL = "https://app.asana.com/api/1.0/tasks/%s/addProject";

    private final HttpClient httpClient;
    private final String token;
    private final String sectionId;

    public TaskMoverRequest(HttpClient httpClient, String token, String sectionId) {
        this.httpClient = httpClient;
        this.token = token;
        this.sectionId = sectionId;
    }

    @Override
    public void moveToSection(TaskInfo task) {
        try {
            var body = new AddProjectBody(task.getProjectId(), sectionId);
            var bodyPublisher = HttpRequest.BodyPublishers.ofString(
                    new ObjectMapper().writeValueAsString(body)
            );
            var uri = new URI(String.format(URL, task.getTaskId()));
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