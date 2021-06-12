package meugeninua.asana.sectionupdates.app;

import meugeninua.asana.sectionupdates.app.tasks.TaskInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Args {

    private static final String KEY_TOKEN = "token";
    private static final String KEY_SECTION_ID = "section";
    private static final String KEY_COMMENT = "comment";
    private static final String KEY_PROJECT_ID = "project-id";
    private static final String KEY_TASK_ID = "task-id";

    public static Args from(String[] args) {
        var arguments = new HashMap<String, String>();

        String key = null;
        for (String arg : args) {
            if (arg.startsWith("-")) {
                key = arg.substring(1);
                continue;
            }
            if (key != null) {
                arguments.put(key, arg);
            }
        }
        return new Args(arguments);
    }

    private final Map<String, String> arguments;

    public Args(Map<String, String> arguments) {
        this.arguments = Objects.requireNonNull(arguments);
    }

    public String getToken() {
        return Objects.requireNonNull(arguments.get(KEY_TOKEN));
    }

    public String getSection() {
        return Objects.requireNonNull(arguments.get(KEY_SECTION_ID));
    }

    public Optional<String> getComment() {
        return Optional.ofNullable(arguments.get(KEY_COMMENT));
    }

    public Optional<TaskInfo> getTask() {
        var projectId = arguments.get(KEY_PROJECT_ID);
        var taskId = arguments.get(KEY_TASK_ID);
        if (projectId == null || taskId == null) return Optional.empty();
        return Optional.of(new TaskInfo(projectId, taskId));
    }
}
