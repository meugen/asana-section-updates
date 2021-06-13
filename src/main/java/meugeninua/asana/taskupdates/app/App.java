package meugeninua.asana.taskupdates.app;

import meugeninua.asana.taskupdates.app.tasks.TaskUpdater;
import meugeninua.asana.taskupdates.app.tasks.TasksIterable;

import java.util.Objects;

public class App {

    private final TaskUpdater mover;
    private final Args args;

    public App(TaskUpdater mover, Args args) {
        this.mover = Objects.requireNonNull(mover);
        this.args = Objects.requireNonNull(args);
    }

    public void run() {
        var request = mover.makeRequest(args.getToken(), args.getSection());
        args.getComment().ifPresent(comment -> {
            new TasksIterable(comment).forEach(request);
        });
        args.getTask().ifPresent(request);
    }
}
