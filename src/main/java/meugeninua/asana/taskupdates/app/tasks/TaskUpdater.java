package meugeninua.asana.taskupdates.app.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import meugeninua.asana.taskupdates.app.models.AddProjectBody;
import meugeninua.asana.taskupdates.app.tasks.requests.MoveToSectionRequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.function.Consumer;

public class TaskUpdater {

    private final HttpClient httpClient;

    public TaskUpdater(HttpClient httpClient) {
        this.httpClient = Objects.requireNonNull(httpClient);
    }

    public Consumer<TaskInfo> makeRequest(String token, String sectionId) {
        return new MoveToSectionRequest(
            httpClient,
            Objects.requireNonNull(token),
            Objects.requireNonNull(sectionId)
        );
    }
}