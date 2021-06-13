package meugeninua.asana.taskupdates;

import meugeninua.asana.taskupdates.app.App;
import meugeninua.asana.taskupdates.app.Args;
import meugeninua.asana.taskupdates.app.tasks.TaskUpdater;

import java.net.http.HttpClient;

public class Program {

    public static void main(String[] args) {
        new App(
            new TaskUpdater(HttpClient.newHttpClient()),
            Args.from(args)
        ).run();
    }
}
