package org.legend8883.guitarResaleSimulator.Player;

import java.util.HashMap;

public class PlayerValues {
    private static double balance = 200000;
    private static HashMap<String, Integer> guitars = new HashMap<>();

    static {
        guitars.put("Classical", 0);
        guitars.put("Acoustic", 0);
        guitars.put("Electric", 0);
    }

    public PlayerValues() {

    }

    public double getBalance() {
        if (balance < 0) {
            System.out.println("Вы проиграли! Ваш баланс опустился ниже нуля!");
            System.exit(0);
        }
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
        if (balance < 0) {
            System.out.println("Вы проиграли! Ваш баланс опустился ниже нуля!");
            System.exit(0);
        }
    }

    public HashMap<String, Integer> getGuitars() {
        return guitars;
    }

    public void setGuitars(HashMap<String, Integer> guitars) {
        this.guitars = guitars;
    }
}
