package com.takeaway.scoobergame.infrastructure.configurations;

import com.takeaway.scoobergame.application.ScooberGameService;
import com.takeaway.scoobergame.domain.model.aggregates.ScooberGame;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ScooberGameService scooberGameService() {
        return new ScooberGameService();
    }

    @Bean
    public ScooberGame scooberGame() {
        return new ScooberGame();
    }
}
