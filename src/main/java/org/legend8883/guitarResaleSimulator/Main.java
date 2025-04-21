package org.legend8883.guitarResaleSimulator;

import org.legend8883.guitarResaleSimulator.Menu.Menu;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        Menu menu = context.getBean("menuBean", Menu.class);
        menu.open();

        context.close();
    }
}
