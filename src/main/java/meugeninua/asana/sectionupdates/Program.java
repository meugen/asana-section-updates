package meugeninua.asana.sectionupdates;

import meugeninua.asana.sectionupdates.app.App;
import meugeninua.asana.sectionupdates.app.Args;
import meugeninua.asana.sectionupdates.app.tasks.TaskMover;

import java.net.http.HttpClient;

public class Program {

    public static void main(String[] args) {
        new App(
            new TaskMover(HttpClient.newHttpClient()),
            Args.from(args)
        ).run();
    }
}
