package org.legend8883.guitarResaleSimulator.Spring;

import org.legend8883.guitarResaleSimulator.Player.PlayerValues;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@org.springframework.context.annotation.Configuration
@PropertySource("classpath:program.properties")

public class AppConfiguration {
    @Bean
    public PlayerValues playerBean() {
        return new PlayerValues();
    }
}
