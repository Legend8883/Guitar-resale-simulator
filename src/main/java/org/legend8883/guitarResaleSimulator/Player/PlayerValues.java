package org.legend8883.guitarResaleSimulator.Player;

import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerValues {
    private static double balance = 200000;
    private static HashMap<String, Integer> guitars;

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
