package meugeninua.asana.taskupdates.app.tasks.requests;

import meugeninua.asana.taskupdates.app.tasks.TaskInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.function.Consumer;

abstract class AbstractRequest implements Consumer<TaskInfo> {

    private final HttpClient httpClient;
    private final String token;

    AbstractRequest(HttpClient httpClient, String token) {
        this.httpClient = Objects.requireNonNull(httpClient);
        this.token = Objects.requireNonNull(token);
    }

    @Override
    public final void accept(TaskInfo taskInfo) {
        try {
            var request = newHttpRequestBuilder(taskInfo)
                .header("Authorization", "Bearer " + token)
                .build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(response.body());
            }
        } catch (Exception e) {
            if (e instanceof RuntimeException)
                throw (RuntimeException) e;
            throw new RuntimeException(e);
        }
    }

    protected abstract HttpRequest.Builder newHttpRequestBuilder(TaskInfo taskInfo) throws Exception;
}
