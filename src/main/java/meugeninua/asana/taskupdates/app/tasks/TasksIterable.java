package meugeninua.asana.taskupdates.app.tasks;

import java.util.Iterator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TasksIterable implements Iterable<TaskInfo> {

    private final String comment;

    public TasksIterable(String comment) {
        this.comment = Objects.requireNonNull(comment);
    }

    @Override
    public Iterator<TaskInfo> iterator() {
        return new TasksIterator(comment);
    }
}

class TasksIterator implements Iterator<TaskInfo> {

    private final Matcher matcher;

    public TasksIterator(String comment) {
        var pattern = Pattern.compile("https://app.asana.com/\\d+/(\\d+)/(\\d+)/?");
        this.matcher = pattern.matcher(comment);
    }

    @Override
    public boolean hasNext() {
        return matcher.find();
    }

    @Override
    public TaskInfo next() {
        return new TaskInfo(matcher.group(1), matcher.group(2));
    }
}