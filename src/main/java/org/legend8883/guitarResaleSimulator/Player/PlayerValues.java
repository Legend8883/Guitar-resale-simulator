package org.legend8883.guitarResaleSimulator.Player;

import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;

public class PlayerValues {
    @Value("#{new Double('${player.balance.start}')}")
    private double balance;
    private ArrayList<String> guitars;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<String> getGuitars() {
        return guitars;
    }

    public void setGuitars(ArrayList<String> guitars) {
        this.guitars = guitars;
    }
}
