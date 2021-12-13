package ru.filit.mdma.dms;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import ru.filit.mdma.dms.web.exception.RestResponseErrorHandler;

@SpringBootApplication(scanBasePackages = {"ru.filit.mdma.dms"})
public class Application {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder templateBuilder) {
        return templateBuilder
                .errorHandler(new RestResponseErrorHandler())
                .build();
    }

    @Bean
    public Ignite ignite() {
        return Ignition.start();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
