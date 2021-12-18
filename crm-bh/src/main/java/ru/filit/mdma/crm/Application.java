package ru.filit.mdma.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.filit.mdma.crm.config.DefaultIntegrationConfig;

@SpringBootApplication
@Import(DefaultIntegrationConfig.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
