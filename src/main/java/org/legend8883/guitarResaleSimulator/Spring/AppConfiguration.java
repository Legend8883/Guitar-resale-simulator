package org.legend8883.guitarResaleSimulator.Spring;

import org.legend8883.guitarResaleSimulator.Player.PlayerValues;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration

public class AppConfiguration {
    @Bean
    public PlayerValues playerBean() {
        return new PlayerValues();
    }
}
