package ru.filit.mdma.dm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;

@SpringBootApplication(scanBasePackages = {"ru.filit.mdma.dm"})
@PropertySource("classpath:config/app.properties")
public class Application {

    @Value("${date.time.pattern}")
    private String dateTimePattern;
    private ObjectMapper objectMapperYaml;
    private ObjectMapper objectMapper;

    @PostConstruct
    private void appCustomize() {
        objectMapperYaml = new ObjectMapper(new YAMLFactory());
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat(dateTimePattern));
    }

    @Bean
    public ObjectMapper objectMapperYaml() {
        return objectMapperYaml;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return objectMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
