package ru.filit.mdma.dms.web.exception;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import ru.filit.mdma.dms.util.exception.DmException;

import java.io.IOException;

public class RestResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (response.getStatusCode().is4xxClientError() ||
                response.getStatusCode().is5xxServerError());
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        byte[] bytes = response.getBody().readAllBytes();
        throw new DmException(new String(bytes));
    }
}