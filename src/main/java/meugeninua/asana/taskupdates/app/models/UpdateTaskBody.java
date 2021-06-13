package meugeninua.asana.taskupdates.app.models;

public class UpdateTaskBody {

    private final Data data;

    public UpdateTaskBody(Data data) {
        this.data = data;
    }

    public UpdateTaskBody(boolean completed) {
        this(new Data(completed));
    }

    public Data getData() {
        return data;
    }

    public static class Data {

        private final boolean completed;

        public Data(boolean completed) {
            this.completed = completed;
        }

        public boolean isCompleted() {
            return completed;
        }
    }
}
