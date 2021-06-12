package meugeninua.asana.sectionupdates.app;

import meugeninua.asana.sectionupdates.app.tasks.TaskMover;
import meugeninua.asana.sectionupdates.app.tasks.TasksIterable;

import java.net.http.HttpClient;
import java.util.Objects;

public class App {

    private final TaskMover mover;
    private final Args args;

    public App(TaskMover mover, Args args) {
        this.mover = Objects.requireNonNull(mover);
        this.args = Objects.requireNonNull(args);
    }

    public void run() {
        var request = mover.makeRequest(args.getToken(), args.getSection());
        args.getComment().ifPresent(comment -> {
            new TasksIterable(comment).forEach(request::moveToSection);
        });
        args.getTask().ifPresent(request::moveToSection);
    }
}
