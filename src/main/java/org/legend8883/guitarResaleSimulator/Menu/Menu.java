package org.legend8883.guitarResaleSimulator.Menu;

import org.legend8883.guitarResaleSimulator.Client.BuyingGuitarFromClient;
import org.legend8883.guitarResaleSimulator.Client.SellingGuitarToClient;
import org.legend8883.guitarResaleSimulator.Player.PlayerValues;
import org.legend8883.guitarResaleSimulator.Spring.AppConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class Menu {
    public void open() {
        Scanner scanner = new Scanner(System.in);
        ClassPathXmlApplicationContext ClassContextClassPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        String optionStr;
        int optionInt = 0;


        while (optionInt == 0) {

            System.out.println("Меню приложения \"Симулятор перекупа гитар\"");
            System.out.println(
                    """
                            Выберите действие:
                            1. Купить гитару
                            2. Продать имеющуюся гитару
                            3. Посмотреть свой баланс
                            4. Посмотреть список имеющихся гитар
                            5. Выход""");

            optionStr = scanner.nextLine();

            try {
                optionInt = Integer.parseInt(optionStr);
                PlayerValues playerValues = annotationConfigApplicationContext.getBean("playerBean", PlayerValues.class);

                switch (optionInt) {

                    case 1:
                        BuyingGuitarFromClient buyingGuitarFromClient = ClassContextClassPathXmlApplicationContext.getBean("buyBean", BuyingGuitarFromClient.class);
                        buyingGuitarFromClient.generateDialog();
                        break;
                    case 2:
                        SellingGuitarToClient sellingGuitarToClient = ClassContextClassPathXmlApplicationContext.getBean("sellBean", SellingGuitarToClient.class);
                        sellingGuitarToClient.sell();
                        break;
                    case 3:
                        System.out.println("Ваш баланс составляет " + playerValues.getBalance() + " рублей");
                        optionInt = 0;
                        break;
                    case 4:
                        if (playerValues.getGuitars() == null) {
                            System.out.println("У вас нету гитар на складе");
                        } else {
                            System.out.println("Список ваших гитар на складе");
                            ArrayList<String> keys = new ArrayList<>(playerValues.getGuitars().keySet());

                            for (int i = 0; i < keys.size(); i++) {
                                System.out.println("Тип гитары: " + keys.get(i) + ", количество: " + playerValues.getGuitars().get(keys.get(i)));
                            }
                        }
                        optionInt = 0;
                        break;
                    case 5:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Вы ввели несуществующий параметр");
                        optionInt = 0;
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Вы не ввели целочисленное значение");
            }
            System.out.println();
        }


        ClassContextClassPathXmlApplicationContext.close();
        scanner.close();
    }
}
