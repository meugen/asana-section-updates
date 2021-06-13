package meugeninua.asana.taskupdates.app;

import meugeninua.asana.taskupdates.app.tasks.TaskInfo;
import meugeninua.asana.taskupdates.app.tasks.TaskUpdater;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;

public class AppTest {

    private static final String TOKEN = "token";
    private static final String SECTION_ID = "section_id";

    @Mock
    private TaskUpdater mover;
    @Mock
    private Consumer<TaskInfo> request;
    @Mock
    private Args args;
    private AutoCloseable closeable;

    private App app;

    @Before
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        Mockito.when(args.getToken()).thenReturn(TOKEN);
        Mockito.when(args.getSection()).thenReturn(SECTION_ID);
        Mockito.when(mover.makeRequest(TOKEN, SECTION_ID)).thenReturn(request);

        app = new App(mover, args);
    }

    @After
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void run_commentWithTwoAsanaUrls_movedToSectionCalledTwoTimes() {
        // given
        var comment = "* Description of task 1 \n"
            + "https://app.asana.com/0/1200462412996304/1200462412996307 \n"
            + "* Description of task 2 \n"
            + "https://app.asana.com/0/1200462412996304/1200462412996313 \n"
            + "additional notes";
        Mockito.when(args.getComment()).thenReturn(Optional.of(comment));

        // when
        app.run();

        // than
        Mockito.verify(request, Mockito.times(2)).accept(any());
    }

    @Test
    public void run_commentWithAsanaUrls_movedToSectionCalledWithValidTask() {
        // given
        var comment = "* Description of the task \n"
            + "https://app.asana.com/0/1200462412996304/1200462412996313 \n"
            + "additional notes";
        Mockito.when(args.getComment()).thenReturn(Optional.of(comment));

        // when
        app.run();

        // than
        var taskCaptor = ArgumentCaptor.forClass(TaskInfo.class);
        Mockito.verify(request).accept(taskCaptor.capture());
        Assert.assertEquals(taskCaptor.getValue(),
            new TaskInfo("1200462412996304", "1200462412996313")
        );
    }
}
