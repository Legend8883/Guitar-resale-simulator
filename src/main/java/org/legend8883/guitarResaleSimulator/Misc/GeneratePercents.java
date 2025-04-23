package org.legend8883.guitarResaleSimulator.Misc;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component("percentsBean")

public class GeneratePercents {
    private final Random random = new Random();
    public int generate() {
        return random.nextInt(20 - 4) + 5;
    }
}
