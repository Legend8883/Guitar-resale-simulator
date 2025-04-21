package org.legend8883.guitarResaleSimulator.Client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

@Component("buyBean")

public class BuyingGuitarFromClient {
    private Random random = new Random();

    @Value("${client.names}")
    private String names;

    public void buy() {

        int randomValue;

        ArrayList<String> namesList = new ArrayList<>();
        for (String name : names.split(", ")) {
            namesList.add(name);
        }

        randomValue = random.nextInt(0, 5);

        String name = namesList.get(randomValue);

        System.out.println("Покупка гитары у " + name);
    }
}
