package meugeninua.asana.taskupdates.app.models;

public class AddProjectBody {

    private final Data data;

    public AddProjectBody(Data data) {
        this.data = data;
    }

    public AddProjectBody(String project, String section) {
        this(new Data(project, section));
    }

    public Data getData() {
        return data;
    }

    public static class Data {

        private final String project;
        private final String section;

        public Data(String project, String section) {
            this.project = project;
            this.section = section;
        }

        public String getProject() {
            return project;
        }

        public String getSection() {
            return section;
        }
    }
}
