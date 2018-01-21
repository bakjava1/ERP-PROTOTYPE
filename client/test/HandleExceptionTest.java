import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import at.ac.tuwien.sepm.assignment.group02.client.util.HandleException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.nio.charset.StandardCharsets;

public class HandleExceptionTest {

    @Test
    public void handleHttpStatusCodeException() throws Exception {

        HttpStatusCodeException exception = new HttpClientErrorException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "",
                ("{\"timestamp\":1516540559104," +
                        "\"status\":500," +
                        "\"error\":\"Not Found\"," +
                        "\"exception\":\"at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException\"," +
                        "\"message\":\"bla bla bla\"," +
                        "\"path\":\"/getAllOpenAssignments\"}").getBytes(),
                StandardCharsets.UTF_8
                );
        try {
            HandleException.handleHttpStatusCodeException(exception);
        } catch (PersistenceLayerException e){
            Assert.assertTrue(e.getMessage().contains("bla bla bla"));
        }
    }

    @Test
    public void handleHttpStatusCodeException_weirdHttpStatusCodeException() throws Exception {

        HttpStatusCodeException exception = new HttpClientErrorException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "",
                ("{\"timestamp\":???," +
                        "\"status\":???," +
                        "\"error\":\"???\"," +
                        "\"exception\":\"???\"," +
                        "\"message\":\"???\"," +
                        "\"path\":\"???\"}").getBytes(),
                StandardCharsets.UTF_8
        );
        try {
            HandleException.handleHttpStatusCodeException(exception);
        } catch (PersistenceLayerException e){
            Assert.assertTrue(e.getMessage().contains("Fehlermeldung konnte nicht interpretiert werden."));
        }
    }

    @Test
    public void handleHttpStatusCodeException_weirdHttpStatusCodeException2() throws Exception {

        HttpStatusCodeException exception = new HttpClientErrorException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "",
                ("<hallo bussi>").getBytes(),
                StandardCharsets.UTF_8
        );
        try {
            HandleException.handleHttpStatusCodeException(exception);
        } catch (PersistenceLayerException e){
            Assert.assertTrue(e.getMessage().contains("Fehlermeldung konnte nicht interpretiert werden."));
        }
    }

    @Test
    public void handleHttpStatusCodeException_noResponseBody() throws Exception {

        HttpStatusCodeException exception = new HttpClientErrorException(
                HttpStatus.NOT_FOUND,
                "",
                null,
                StandardCharsets.UTF_8
        );
        try {
            HandleException.handleHttpStatusCodeException(exception);
        } catch (PersistenceLayerException e){
            Assert.assertTrue(e.getMessage().contains("Fehlermeldung konnte nicht interpretiert werden."));
        }
    }

    @Test
    public void handleHttpStatusCodeException_noMessage() throws Exception {



        HttpStatusCodeException exception = new HttpClientErrorException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "",
                ("{\"timestamp\":1516540559104," +
                        "\"status\":500," +
                        "\"error\":\"Not Found\"," +
                        "\"exception\":\"at.ac.tuwien.sepm.assignment.group02.server.exceptions.ServiceLayerException\"," +
                        //"\"message\":\"bla bla bla\"," +
                        "\"path\":\"/getAllOpenAssignments\"}").getBytes(),
                StandardCharsets.UTF_8
        );
        try {
            HandleException.handleHttpStatusCodeException(exception);
        } catch (PersistenceLayerException e){
            Assert.assertTrue(e.getMessage().contains("Fehlermeldung konnte nicht interpretiert werden."));
        }
    }
}