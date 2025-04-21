package org.legend8883.guitarResaleSimulator.Client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

@Component("buyBean")

public class BuyingGuitarFromClient {
    private Random random = new Random();

    @Value("${client.names}")
    private String clientNames;
    @Value("${guitar.names}")
    private String guitarName;

    public void buy() {

        int randomValue;

        //Генерация случайного имени с файла
        ArrayList<String> namesList = new ArrayList<>();

        for (String name : clientNames.split(", ")) {
            namesList.add(name);
        }

        randomValue = random.nextInt(namesList.size());

        String clientName;
        clientName = namesList.get(randomValue);

        System.out.println("Покупка гитары у " + clientName);


        //Генерация случайной цены относительно той, которая указана в файле
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
        try {
            double guitarPrice = Double.parseDouble(guitarsList.get(1));

            System.out.println(
                    "Тип гитары: " + guitarName + "\n" +
                    "Цена: " + guitarPrice
            );
        } catch (NumberFormatException e) {
            System.out.println("Введите double значение для гитары " + guitarName + " в файле names.properties");
        }


    }
}
