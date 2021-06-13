package meugeninua.asana.taskupdates.app.tasks;

import java.util.Objects;

public class TaskInfo {
    private final String projectId;
    private final String taskId;

    public TaskInfo(String projectId, String taskId) {
        this.projectId = Objects.requireNonNull(projectId);
        this.taskId = Objects.requireNonNull(taskId);
    }

    public String getProjectId() {
        return projectId;
    }

    public String getTaskId() {
        return taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskInfo taskInfo = (TaskInfo) o;
        return Objects.equals(projectId, taskInfo.projectId)
            && Objects.equals(taskId, taskInfo.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, taskId);
    }
}