package ru.filit.mdma.dms.web.exception;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.filit.mdma.dms.util.error.ErrorInfo;
import ru.filit.mdma.dms.util.exception.DmException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DmException.class})
    protected ResponseEntity<ErrorInfo> handleHttpClientError(DmException ex, HttpServletRequest request) {
        return getErrorInfo(ex, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ErrorInfo errorInfo = new ErrorInfo(LocalDateTime.now().toString(), HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(), getURI(request));
        return ResponseEntity.status(errorInfo.getStatus()).body(errorInfo);
    }

    private ResponseEntity<ErrorInfo> getErrorInfo(Exception ex, HttpServletRequest request) {
        Map<String, Object> map = new Gson().fromJson(ex.getMessage(),
                new TypeToken<HashMap<String, Object>>() {
                }.getType());
        String timestamp = String.valueOf(map.get("timestamp"));
        int status = Double.valueOf(String.valueOf(map.get("status"))).intValue();
        String message = String.valueOf(map.get("message"));
        return ResponseEntity.status(status).body(new ErrorInfo(timestamp,
                status, message, request.getRequestURI()));
    }

    private String getURI(WebRequest request) {
        StringBuilder sb = new StringBuilder(request.getDescription(true));
        return sb.substring(sb.indexOf("/"), sb.indexOf(";"));
    }
}