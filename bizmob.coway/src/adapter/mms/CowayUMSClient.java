package adapter.mms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

public class CowayUMSClient {

    private static final Logger logger = LoggerFactory.getLogger(CowayUMSClient.class);

    public Map<String, Object> callUMSApi (CowayUMSRequestDO cowayUMSRequestDO) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseErrorHandler responseErrorHandler = new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                logger.warn("response status: '{}', headers: '{}', body: '{}'",
                        response.getStatusCode(), response.getHeaders(), response.getBody());
            }
        };

        restTemplate.setErrorHandler(responseErrorHandler);
        // Add the Jackson message converter
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Gson gson = new GsonBuilder().create();

        String json = gson.toJson(cowayUMSRequestDO);

        HttpEntity<String> requestParam = new HttpEntity<>(json, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(CowayUMSInfo.getUrl(), requestParam , String.class);
        logger.error(responseEntity.toString());

        return gson.fromJson(responseEntity.getBody(), Map.class);

    }
}
