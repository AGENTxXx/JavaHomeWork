package ru.innopolis.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.SQLTransactionRollbackException;
import java.util.*;

/**
 * Created by Alexander Chuvashov on 07.12.2016.
 */

class Command {
    int arg1;
    int arg2;

    String com;

    public Command(String com, int arg1, int arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.com = com;
    }
}

public class Calculator {

    int result = 0;

    List<Command> commands = new ArrayList<>();

    public int add(int a, int b) {
        return a+b;
    }

    public int mutt(int a, int b) {
        return a*b;
    }

    public int div(int a, int b) {
        return a/b;
    }

    public int sub(int a, int b) {
        return a-b;
    }

    public void addCommand(String com, int arg1, int arg2) {
        commands.add(new Command(com, arg1,arg2));
    }


    public Command getCommand() {
        if (commands.size() == 0) {
            return null;
        }
        Command cm = commands.get(0);
        commands.remove(0);
        return cm;
    }

    public void callCommand(Command c) {
        switch (c.com) {
            case "add":
                result += add(c.arg1,c.arg2);
                break;
            case "mutt":
                result += mutt(c.arg1,c.arg2);
                break;
            case "div":
                result += div(c.arg1,c.arg2);
                break;
            case "sub":
                result += sub(c.arg1,c.arg2);
                break;
        }
    }

    public void runCalling() {
        Command c;
        while (commands.size() > 0) {
            c = getCommand();
            callCommand(c);
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
        Calculator cl = new Calculator();
        Scanner scanInput = new Scanner(System.in);
        boolean exit = false;
        System.out.println("Program Calculator!\nPlease enter command: \nADD \nMUTT \nDIV \nSUB \n\n If you want to start calculate - enter RUN and press Enter.\n\n For exit - EXIT");
        String data;
        String[] userCmd;
        while (!exit) {
            data= scanInput.nextLine();
            userCmd = data.split(" ");
            if (!"run".equals(data) && !"exit".equals(data)) {
                cl.addCommand(userCmd[0],Integer.valueOf(userCmd[1]),Integer.valueOf(userCmd[2]));
            }
            else if ("run".equals(data)) {
                cl.runCalling();
            }
            else if ("exit".equals(data)) {
                exit = true;
            }

        }

    }


}