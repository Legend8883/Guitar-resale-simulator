package org.legend8883.guitarResaleSimulator.Client;

import org.legend8883.guitarResaleSimulator.Misc.GeneratePercents;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

@Component("buyBean")

public class BuyingGuitarFromClient {
    private Random random = new Random();
    private int randomValue;

    private ArrayList<String> guitarsList = new ArrayList<>();
    private String guitarName;

    @Value("${client.names}")
    private String clientNames;
    @Value("${guitar.names}")
    private String guitarNames;

    public void buy() {
        generateClientName();
        generateGuitarName();
        generateGuitarPrice();
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
    public void generateGuitarName() {

        for (String guitarName : guitarNames.split(", ")) {
            guitarsList.add(guitarName);
        }

        randomValue = random.nextInt(guitarsList.size());

        String guitarTemp = guitarsList.get(randomValue);

        guitarsList.clear();

        for (String guitar : guitarTemp.split("=")) {
            guitarsList.add(guitar);
        }
        guitarName = guitarsList.get(0);
    }

    public void generateGuitarPrice() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        double guitarPrice = 0;
        double guitarMinPrice = 0;
        int genPercents = 0;

        try {
            guitarPrice = Double.parseDouble(guitarsList.get(1));

            GeneratePercents generatePercents = context.getBean("percentsBean", GeneratePercents.class);
            genPercents = generatePercents.generate();

            guitarMinPrice = guitarPrice - (guitarPrice * genPercents / 100);

        } catch (NumberFormatException e) {
            System.out.println("Введите double значение для гитары " + guitarName + " в файле names.properties");
        }

        System.out.println(
                "Тип гитары: " + guitarName + "\n" +
                        "Цена:" + guitarPrice + "\n" +
                        "Проценты: " + genPercents + "\n" +
                        "Минимальная цена гитары: " + guitarMinPrice
        );

        context.close();
    }
}
