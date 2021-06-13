package meugeninua.asana.taskupdates.app.tasks.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import meugeninua.asana.taskupdates.app.models.UpdateTaskBody;
import meugeninua.asana.taskupdates.app.tasks.TaskInfo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class MarkCompletedRequest extends AbstractRequest {

    private static final String URL = "https://app.asana.com/api/1.0/tasks/%s";

    public MarkCompletedRequest(HttpClient httpClient, String token) {
        super(httpClient, token);
    }

    @Override
    protected HttpRequest.Builder newHttpRequestBuilder(TaskInfo taskInfo) throws Exception {
        var body = new UpdateTaskBody(true);
        var bodyPublisher = HttpRequest.BodyPublishers.ofString(
            new ObjectMapper().writeValueAsString(body)
        );
        var uri = new URI(String.format(URL, taskInfo.getTaskId()));
        return HttpRequest.newBuilder(uri).PUT(bodyPublisher);
    }
}
