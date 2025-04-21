package org.legend8883.guitarResaleSimulator.Client;

import org.legend8883.guitarResaleSimulator.Misc.GeneratePercents;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

@Component("buyBean")

public class BuyingGuitarFromClient {
    private Random random = new Random();
    private int randomValue;

    @Value("${client.names}")
    private String clientNames;
    @Value("${guitar.names}")
    private String guitarName;

    public void buy() {
        generateClientName();
        generateGuitar();

    }

    //Генерация случайного имени с файла
    public void generateClientName() {

        ArrayList<String> namesList = new ArrayList<>();

        for (String name : clientNames.split(", ")) {
            namesList.add(name);
        }

        randomValue = random.nextInt(namesList.size());

        String clientName;
        clientName = namesList.get(randomValue);

        System.out.println("Покупка гитары у " + clientName);
    }

    //Генерация случайной цены относительно той, которая указана в файле
    public void generateGuitar() {

        ArrayList<String> guitarsList = new ArrayList<>();

        for (String guitarName : guitarName.split(", ")) {
            guitarsList.add(guitarName);
        }

        randomValue = random.nextInt(guitarsList.size());

        String guitarTemp = guitarsList.get(randomValue);

        guitarsList.clear();

        for (String guitar : guitarTemp.split("=")) {
            guitarsList.add(guitar);
        }
        String guitarName = guitarsList.get(0);

        double guitarPrice = 0;
        int genPercents = 0;

        try {
            guitarPrice = Double.parseDouble(guitarsList.get(1));

            GeneratePercents generatePercents = new GeneratePercents();
            genPercents = generatePercents.generate();

            guitarPrice += guitarPrice * genPercents / 100;

        } catch (NumberFormatException e) {
            System.out.println("Введите double значение для гитары " + guitarName + " в файле names.properties");
        }

        System.out.println(
                "Тип гитары: " + guitarName + "\n" +
                        "Цена:" + guitarPrice + "\n" +
                        "Проценты: " + genPercents
        );
    }
}
