package controller;

import model.GameIO;

import java.util.Scanner;

public class ConsoleIO implements GameIO {
    private final Scanner scanner;

    public ConsoleIO(Scanner scanner) {
        this.scanner = scanner;
    }

    public Scanner getScanner() {   // ✅ AJOUT
        return scanner;
    }

    @Override
    public void println(String s) {
        System.out.println(s);
    }

    @Override
    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
