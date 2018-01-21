import at.ac.tuwien.sepm.assignment.group02.client.configuration.RestTemplateConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RestTemplateConfigTest {

    @Test
    public void getRestTemplate_returnsRestTemplate() throws Exception {
        RestTemplateConfiguration restTemplateConfiguration = new RestTemplateConfiguration();
        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        Assert.assertFalse(messageConverters.isEmpty());
        Assert.assertTrue(messageConverters.get(0).getSupportedMediaTypes().contains(MediaType.APPLICATION_JSON));
        Assert.assertTrue(messageConverters.get(0).getSupportedMediaTypes().contains(MediaType.APPLICATION_JSON_UTF8));
        Assert.assertTrue(messageConverters.get(0).getSupportedMediaTypes().contains(MediaType.TEXT_PLAIN));
    }

}