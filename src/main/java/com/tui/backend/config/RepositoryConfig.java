package com.tui.backend.config;

import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public RepositoryService gitHubRepositoryService() {
        return new RepositoryService();
    }

}