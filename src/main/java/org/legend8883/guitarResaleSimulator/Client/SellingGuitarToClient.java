package org.legend8883.guitarResaleSimulator.Client;

import org.legend8883.guitarResaleSimulator.Menu.Menu;
import org.legend8883.guitarResaleSimulator.Misc.GeneratePercents;
import org.legend8883.guitarResaleSimulator.Player.PlayerValues;
import org.legend8883.guitarResaleSimulator.Spring.AppConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

@Component("sellBean")
public class SellingGuitarToClient {

    @Value("${client.names}")
    private String clientNames;
    @Value("${guitar.names}")
    private String guitarNames;

    private String clientName;

    private final Random random = new Random();
    private int randomValue;

    private final Scanner scanner = new Scanner(System.in);
    private boolean caught = false;

    private final ArrayList<String> guitarsList = new ArrayList<>();
    private String guitarName;

    private int genPercents = 0;
    private double guitarPrice = 0;
    private double guitarMaxPrice = 0;

    private final AnnotationConfigApplicationContext PlayerContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
    private final PlayerValues playerValues = PlayerContext.getBean("playerBean", PlayerValues.class);

    private double userPriceDouble = 0;

    private ArrayList<String> keys = new ArrayList<>(playerValues.getGuitars().keySet());

    private ArrayList<String> selectedGuitar = new ArrayList<>();

    public void test() {
            generateGuitarName();
    }

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
                    "Продажа гитары клиенту " + clientName + "\n" +
                            "Тип желаемой гитары клиентом: " + guitarName + "\n" +
                            "Средняя цена данной гитары:" + guitarPrice + "\n" +
                            "Максимальный процент наценки, ожидаемый клиентом: " + genPercents + "\n" +
                            "Максимальная цена гитары, ожидаемая клиентом: " + guitarMaxPrice + "\n" +
                            "Ваш баланс равен " + playerValues.getBalance()
            );
            getUserGuitars();

            System.out.println("""
                    
                    Будете ли вы продавать эту гитару?
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
//                        buyGuitarMenu();
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
    private void generateClientName() {

        ArrayList<String> namesList = new ArrayList<>();

        for (String name : clientNames.split(", ")) {
            namesList.add(name);
        }

        randomValue = random.nextInt(namesList.size());

        clientName = namesList.get(randomValue);
    }

    private void getUserGuitars() {
        if (playerValues.getGuitars() == null) {
            System.out.println("У вас нету гитар на складе");
        } else {
            System.out.println("Список ваших гитар на складе");

            for (int i = 0; i < keys.size(); i++) {
                System.out.println("Тип гитары: " + keys.get(i) + ", количество: " + playerValues.getGuitars().get(keys.get(i)));
            }
        }
    }

    //Генерация случайной цены относительно той, которая указана в файле
    private void generateGuitarName() {

        guitarsList.clear();

        for (String guitarName : guitarNames.split(", ")) {
            guitarsList.add(guitarName);
        }

        boolean cycleOn = true;
        String guitarTemp;

        while (cycleOn) {
            selectedGuitar.clear();

            randomValue = random.nextInt(guitarsList.size());
            guitarTemp = guitarsList.get(randomValue);

            for (String guitar : guitarTemp.split("=")) {
                selectedGuitar.add(guitar);
            }
            guitarName = selectedGuitar.get(0);

            ArrayList<String> availableGuitars = new ArrayList<>();
            availableGuitars = keys;

            int removeValue = 0;

            for(int i = 0; i < guitarsList.size(); i++) {
                if (availableGuitars.get(i).equals(guitarName)) {
                    removeValue = i;
                }
                if (guitarName.equals(availableGuitars.get(i)) && playerValues.getGuitars().get(availableGuitars.get(i)) >= 1) {
                    cycleOn = false;
                    break;
                }
            }

            guitarsList.remove(randomValue);
            availableGuitars.remove(removeValue);

            if (guitarsList.size() == 0) {
                cycleOn = false;
                System.out.println("У тебя нету гитар, как ты сюда пробрался?");
                System.exit(0);
            }
        }

        System.out.println(guitarName);

    }

    private void generateGuitarPrice() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        try {
            guitarPrice = Double.parseDouble(selectedGuitar.get(1));

            GeneratePercents generatePercents = context.getBean("percentsBean", GeneratePercents.class);
            genPercents = generatePercents.generate();

            guitarMaxPrice = guitarPrice + (guitarPrice * genPercents / 100);

        } catch (NumberFormatException e) {
            System.out.println("Введите double значение для гитары " + guitarName + " в файле program.properties");
        }

        context.close();
    }

    private void buyGuitarMenu() {
        boolean optionCycle = true;

        while (optionCycle) {
            System.out.println("Введите цену, которую вы хотите заплатить за эту гитару: ");
            System.out.println("Если вы хотите выйти в меню покупки гитар напишите \"exit\"");

            String userPriceStr = scanner.nextLine();

            if (userPriceStr.equals("exit")) {
                caught = true;
                break;
            }
            try {
                userPriceDouble = Double.parseDouble(userPriceStr);
                if (userPriceDouble >= guitarMaxPrice) {
                    setPlayerBalance();
                    setPlayerGuitarList();

                    System.out.println("Вы успешно купили гитару!");

                    optionCycle = false;
                } else {
                    System.out.println("Вы ввели цену, превышающую ожидаемую клиентом");
                }

            } catch (NumberFormatException e) {
                System.out.println("Вы ввели не число");
            }

        }
    }

    private void setPlayerBalance() {
        double balance = playerValues.getBalance();
        playerValues.setBalance(balance - userPriceDouble);
    }

    private void setPlayerGuitarList() {
        HashMap<String, Integer> guitarMap = playerValues.getGuitars();
        int guitarCount = guitarMap.get(guitarName);
        guitarCount++;
        guitarMap.put(guitarName, guitarCount);
        playerValues.setGuitars(guitarMap);
    }
}
