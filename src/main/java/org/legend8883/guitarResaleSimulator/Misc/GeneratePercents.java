package org.legend8883.guitarResaleSimulator.Misc;

import java.util.Random;

public class GeneratePercents {
    private Random random = new Random();
    public int generate() {
        return random.nextInt(20 - 4) + 5;
    }
}
