package ru.filit.mdma.crm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.filit.mdma.dm.web.ApiClient;
import ru.filit.mdma.dm.web.controller.DefaultApi;

@Configuration
public class DefaultIntegrationConfig {
    @Bean
    public DefaultApi defaultApi() {
        return new DefaultApi(apiClient());
    }

    @Bean
    public ApiClient apiClient() {
        return new ApiClient();
    }
}