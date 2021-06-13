package meugeninua.asana.taskupdates.app.tasks;

import meugeninua.asana.taskupdates.app.tasks.requests.MarkCompletedRequest;
import meugeninua.asana.taskupdates.app.tasks.requests.MoveToSectionRequest;

import java.net.http.HttpClient;
import java.util.*;
import java.util.function.Consumer;

public class TaskUpdater {

    private static final String UPDATE_ALL = "all";
    private static final String UPDATE_SECTION = "section";
    private static final String UPDATE_COMPLETED = "completed";

    private final HttpClient httpClient;

    public TaskUpdater(HttpClient httpClient) {
        this.httpClient = Objects.requireNonNull(httpClient);
    }

    public Consumer<TaskInfo> makeRequest(String token, String sectionId) {
        return makeRequest(UPDATE_ALL, token, sectionId);
    }

    public Consumer<TaskInfo> makeRequest(String updates, String token, String sectionId) {
        var updatesSet = new HashSet<>(
            Arrays.asList(updates.split(","))
        );

        Consumer<TaskInfo> request = t -> { };
        if (isUpdateNeeded(updatesSet, UPDATE_SECTION)) {
            request = request.andThen(
                new MoveToSectionRequest(httpClient, token, sectionId)
            );
        }
        if (isUpdateNeeded(updatesSet, UPDATE_COMPLETED)) {
            request = request.andThen(
                new MarkCompletedRequest(httpClient, token)
            );
        }
        return request;
    }

    private boolean isUpdateNeeded(Set<String> updates, String update) {
        if (updates.contains(UPDATE_ALL)) return true;
        return updates.contains(update);
    }
}