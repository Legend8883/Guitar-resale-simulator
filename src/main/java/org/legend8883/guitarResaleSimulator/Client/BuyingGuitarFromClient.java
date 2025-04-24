package org.legend8883.guitarResaleSimulator.Client;

import org.legend8883.guitarResaleSimulator.Menu.Menu;
import org.legend8883.guitarResaleSimulator.Misc.GeneratePercents;
import org.legend8883.guitarResaleSimulator.Player.PlayerValues;
import org.legend8883.guitarResaleSimulator.Spring.AppConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

@Component("buyBean")

public class BuyingGuitarFromClient {

    @Value("${client.names}")
    private String clientNames;
    @Value("${guitar.names}")
    private String guitarNames;

    private final Random random = new Random();
    private int randomValue;

    private String clientName;

    private ArrayList<String> guitarsList = new ArrayList<>();
    private String guitarName;

    private int genPercents = 0;
    private double guitarPrice = 0;
    private double guitarMinPrice = 0;

    private final Scanner scanner = new Scanner(System.in);

    private boolean caught = false;


    public void generateDialog() {
        String optionStr;
        int optionInt = 0;


        while (optionInt == 0) {
            System.out.println();

            if (!caught) {
                generateClientName();
                generateGuitarName();
                generateGuitarPrice();
            }

            System.out.println(
                    "Покупка гитары у " + clientName + "\n" +
                            "Тип гитары: " + guitarName + "\n" +
                            "Цена:" + guitarPrice + "\n" +
                            "Проценты: " + genPercents + "\n" +
                            "Минимальная цена гитары: " + guitarMinPrice
            );

            System.out.println("""
                    
                    Будете ли вы покупать эту гитару?
                    1. Да
                    2. Нет
                    3. Выйти в меню
                    4. Выйти из программы""");

            optionStr = scanner.nextLine();
            try {
                caught = false;

                optionInt = Integer.parseInt(optionStr);
                switch (optionInt) {
                    case 1:
                        buyGuitar();
                        optionInt = 0;
                        break;
                    case 2:
                        optionInt = 0;
                        break;
                    case 3:
                        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
                        Menu menu = context.getBean("menuBean", Menu.class);
                        menu.open();
                        context.close();
                        break;
                    case 4:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Такого параметра нет");
                        optionInt = 0;
                        caught = true;
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Вы не ввели целочисленное значение");
                caught = true;
            }
        }
        scanner.close();
    }

    //Генерация случайного имени с файла
    public void generateClientName() {

        ArrayList<String> namesList = new ArrayList<>();

        for (String name : clientNames.split(", ")) {
            namesList.add(name);
        }

        randomValue = random.nextInt(namesList.size());

        clientName = namesList.get(randomValue);
    }

    //Генерация случайной цены относительно той, которая указана в файле
    public void generateGuitarName() {
        guitarsList.clear();

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

        try {
            guitarPrice = Double.parseDouble(guitarsList.get(1));

            GeneratePercents generatePercents = context.getBean("percentsBean", GeneratePercents.class);
            genPercents = generatePercents.generate();

            guitarMinPrice = guitarPrice - (guitarPrice * genPercents / 100);

        } catch (NumberFormatException e) {
            System.out.println("Введите double значение для гитары " + guitarName + " в файле program.properties");
        }

        context.close();
    }

    public void buyGuitar() {
        boolean optionCycle = true;

        while (optionCycle) {
            System.out.println("Введите цену, которую вы хотите заплатить за эту гитару: ");
            System.out.println("Если вы хотите выйти в меню покупки гитар напишите \"exit\"");

            String userPriceStr = scanner.nextLine();
            double userPriceDouble = 0;

            if (userPriceStr.equals("exit")) {
                caught = true;
                break;
            }

            try {
                userPriceDouble = Double.parseDouble(userPriceStr);
                if (userPriceDouble >= guitarMinPrice) {
                    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
                    PlayerValues playerValues = context.getBean("playerBean", PlayerValues.class);
                    double balance = playerValues.getBalance();
                    playerValues.setBalance(balance - userPriceDouble);
                    System.out.println("Вы успешно купили гитару!");
                    System.out.println(playerValues.getBalance());

                    optionCycle = false;
                    context.close();
                } else {
                    System.out.println("Вы ввели цену, превышающую ожидаемую клиентом");
                }

            } catch (NumberFormatException e) {
                System.out.println("Вы ввели не число");
            }

        }
    }
}
