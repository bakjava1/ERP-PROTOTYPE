package at.ac.tuwien.sepm.assignment.group02.client.util;

import at.ac.tuwien.sepm.assignment.group02.client.exceptions.PersistenceLayerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class HandleException {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void handleHttpStatusCodeException(HttpStatusCodeException e) throws PersistenceLayerException {
        LOG.debug("called handleHttpStatusCodeException: ", e.getResponseBodyAsString());
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY); //throw JsonProcessingException on duplicate key or any other error
        String msg = "Fehlermeldung konnte nicht interpretiert werden. Status Code:";
        try {
            if (mapper.readTree(e.getResponseBodyAsString()) != null) {
                msg = mapper.readTree(e.getResponseBodyAsString()).get("message").asText();
            }
        } catch (JsonProcessingException e0){
            LOG.warn(e0.getMessage().trim()+" "+e.getMessage());
        } catch (IOException e1) {
            LOG.warn(e1.getMessage().trim());
        }
        LOG.warn("HttpStatusCodeException: ", msg);
        throw new PersistenceLayerException("Problem am Server. "+"Status Code "+e.getStatusCode()+": "+msg);
    }
}
